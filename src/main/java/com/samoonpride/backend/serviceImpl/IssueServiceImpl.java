package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.config.LineConfig;
import com.samoonpride.backend.config.ModelMapperConfig;
import com.samoonpride.backend.config.WebClientConfig;
import com.samoonpride.backend.converter.IssueToIssueBubbleDtoConverter;
import com.samoonpride.backend.converter.IssueToNotificationBubbleDtoConverter;
import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.NotificationBubbleDto;
import com.samoonpride.backend.dto.UserDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.ActivityLogAction;
import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.enums.UserEnum;
import com.samoonpride.backend.model.Issue;
import com.samoonpride.backend.repository.IssueRepository;
import com.samoonpride.backend.repository.StaffIssueAssignmentRepository;
import com.samoonpride.backend.repository.SubscribeRepository;
import com.samoonpride.backend.service.IssueService;
import com.samoonpride.backend.utils.LogMessageFormatter;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final StaffIssueAssignmentRepository staffIssueAssignmentRepository;
    private final SubscribeRepository subscribeRepository;
    private final LineUserServiceImpl lineUserService;
    private final MediaServiceImpl multimediaService;
    private final StaffServiceImpl staffService;
    private final ActivityLogServiceImpl activityLogService;

    @Override
    public void createIssue(CreateIssueRequest createIssueRequest) {
        log.info("Creating issue");
        Issue issue = buildIssueFromRequest(createIssueRequest);
        issueRepository.save(issue);

        activityLogService.logAction(
                ActivityLogAction.USER_CREATE_ISSUE,
                issue.getLineUser().getDisplayName() + " (Line User)",
                LogMessageFormatter.formatUserCreateIssue(
                        createIssueRequest.getUser().getUserId(),
                        issue
                )
        );

        // It will set thumbnailPath in createMultimedia
        multimediaService.createMultimedia(issue, createIssueRequest.getMedia());
    }

    private Issue buildIssueFromRequest(CreateIssueRequest request) {
        Issue issue = new Issue();
        setUserForIssue(issue, request.getUser());
        issue.setTitle(request.getTitle());
        if (request.getDuplicateIssueId() != null) {
            Optional<Issue> duplicateIssue = issueRepository.findById(request.getDuplicateIssueId());
            log.info("Duplicate Issue ID: {}", request.getDuplicateIssueId());
            log.info("Duplicate Issue: {}", duplicateIssue.orElse(null));
            issue.setDuplicateIssue(duplicateIssue.orElse(null));
            issue.setStatus(IssueStatus.DUPLICATED);
        } else {
            issue.setStatus(IssueStatus.IN_CONSIDERATION);
        }
        issue.setLatitude(request.getLatitude());
        issue.setLongitude(request.getLongitude());
        return issue;
    }

    private void setUserForIssue(Issue issue, UserDto userDto) {
        if (userDto.getType() == UserEnum.LINE) {
            issue.setLineUser(lineUserService.findByUserId(userDto.getUserId()));
            log.info("Set LineUser: {}", issue.getLineUser());
        } else {
            issue.setStaff(staffService.findStaffByUsername(userDto.getUserId()));
            log.info("Set Staff: {}", issue.getStaff());
        }
    }

    @Override
    public void updateIssueStatus(int issueId, UpdateIssueStatusRequest updateIssueStatusRequest) {
        Issue issue = issueRepository.findById(issueId);
        issue.setStatus(updateIssueStatusRequest.getStatus());
        issueRepository.save(issue);

        // notify when issue status change
        notifyWhenIssueStatusChange(issue);
    }

    @Override
    public void reopenIssue(int issueId, Claims claims) {
        Issue issue = issueRepository.findById(issueId);
        if (issue.getStatus() == IssueStatus.COMPLETED) {
            issue.setStatus(IssueStatus.IN_CONSIDERATION);
            issueRepository.save(issue);

            activityLogService.logAction(
                    ActivityLogAction.STAFF_REOPEN_ISSUE,
                    claims.get("username", String.class),
                    LogMessageFormatter.formatStaffReopenIssue(
                            claims.get("username", String.class),
                            issue
                    )
            );

            // notify when issue status change
            notifyWhenIssueStatusChange(issue);
        }
        throw new IllegalArgumentException("Issue is not status completed");
    }

    @Override
    @Transactional
    public void updateIssue(int issueId, UpdateIssueRequest updateIssueRequest, MultipartFile media, Claims claims) {
        Issue issue = issueRepository.findById(issueId);
        issue.setTitle(updateIssueRequest.getTitle());
        if (updateIssueRequest.getDuplicateIssueId() != null) {
            Optional<Issue> duplicateIssue = issueRepository.findById(updateIssueRequest.getDuplicateIssueId());
            issue.setDuplicateIssue(duplicateIssue.orElse(null));
        }
        if (issue.getStatus() != updateIssueRequest.getStatus()) {
            issue.setStatus(updateIssueRequest.getStatus());
            // notify when issue status change
            notifyWhenIssueStatusChange(issue);
        }
        issue.setAssignees(staffIssueAssignmentRepository.findByIssueIdIn(updateIssueRequest.getAssigneeIds()));
        issue.setLatitude(updateIssueRequest.getLatitude());
        issue.setLongitude(updateIssueRequest.getLongitude());
        issueRepository.save(issue);

        activityLogService.logAction(
                ActivityLogAction.STAFF_UPDATE_ISSUE,
                claims.get("username", String.class),
                LogMessageFormatter.formatStaffUpdateIssue(
                        claims.get("username", String.class),
                        issue,
                        updateIssueRequest
                )
        );

        // It will set thumbnailPath in createMultimedia
        if (media != null) {
            multimediaService.updateIssueMedia(issue, media);
        }
    }

    private void notifyWhenIssueStatusChange(Issue issue) {
        activityLogService.logAction(
                ActivityLogAction.ISSUE_NOTIFICATION,
                "SYSTEM",
                LogMessageFormatter.formatIssueNotification(
                        issue
                )
        );

        WebClientConfig.getWebClient()
                .post()
                .uri(LineConfig.getLineWebhookUrl() + "/notify/issue")
                .bodyValue(createNotificationBubbleDtoList(issue))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    @Override
    public List<NotificationBubbleDto> createNotificationBubbleDtoList(Issue issue) {
        log.info("Notify when issue status change");
        List<NotificationBubbleDto> notificationBubbleDtoList = new ArrayList<>();
        // add yourself to issueNotifyBubbleDtoList
        log.info("Create Self IssueNotifyBubbleDto");
        ModelMapper modelMapperSelf = new ModelMapper();
        modelMapperSelf.addConverter(new IssueToNotificationBubbleDtoConverter(issue.getLineUser().getUserId()));
        NotificationBubbleDto notificationBubbleDtoSelf = modelMapperSelf.map(issue, NotificationBubbleDto.class);
        log.info("IssueNotifyBubbleDto: {}", notificationBubbleDtoSelf);
        notificationBubbleDtoList.add(notificationBubbleDtoSelf);

        // add duplicate issues to issueNotifyBubbleDtoList
        log.info("Create Duplicate IssueNotifyBubbleDto");
        List<Issue> duplicateIssues = issueRepository.findByDuplicateIssueId(issue.getId());
        for (Issue duplicateIssue : duplicateIssues) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addConverter(new IssueToNotificationBubbleDtoConverter(duplicateIssue.getLineUser().getUserId()));
            NotificationBubbleDto notificationBubbleDto = modelMapper.map(duplicateIssue, NotificationBubbleDto.class);
            log.info("IssueNotifyBubbleDto: {}", notificationBubbleDto);
            notificationBubbleDtoList.add(notificationBubbleDto);
        }

        // get all lineUserIds that subscribe to this issue
        log.info("Get all lineUserIds that subscribe to this issue");
        List<String> lineUserIds = issue.getSubscribes().stream()
                .map(subscribe -> subscribe.getLineUser().getUserId())
                .toList();
        log.info("Subscribed lineUserIds: {}", lineUserIds);
        // add subscribed lineUserIds to issueNotifyBubbleDtoList
        log.info("Create Subscribed IssueNotifyBubbleDto");
        ModelMapper modelMapperSubscribed = new ModelMapper();
        modelMapperSubscribed.addConverter(new IssueToNotificationBubbleDtoConverter(lineUserIds));
        NotificationBubbleDto notificationBubbleDtoSubscribed = modelMapperSubscribed.map(issue, NotificationBubbleDto.class);
        notificationBubbleDtoList.add(notificationBubbleDtoSubscribed);
        log.info("IssueNotifyBubbleDto: {}", notificationBubbleDtoSubscribed);

        return notificationBubbleDtoList;
    }

    // get latest 10 issues
    @Override
    public List<IssueBubbleDto> getLatestTenIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new IssueToIssueBubbleDtoConverter(userId));
        List<Issue> issueList = issueRepository.findFirst10ByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(userId, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueBubbleDto> getAllIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new IssueToIssueBubbleDtoConverter(userId));
        List<Issue> issueList = issueRepository.findAllByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(userId, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueBubbleDto> getDistinctIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get distinct issues");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new IssueToIssueBubbleDtoConverter(userId));
        List<Issue> issueList = issueRepository.findFirst10ByLineUser_UserIdNotAndStatusInOrderByCreatedDateDesc(userId, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueDto> getAllIssues() {
        log.info("get all issues");
        ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

        List<Issue> issueList = (List<Issue>) issueRepository.findAll();
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueBubbleDto> getSubscribedIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get subscribed issues");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new IssueToIssueBubbleDtoConverter(userId));
        List<Integer> issueIds = subscribeRepository.findByLineUser_UserId(userId).stream()
                .map(subscribe -> subscribe.getIssue().getId())
                .collect(Collectors.toList());
        log.info("issueIds: " + issueIds);
        return issueRepository.findByIdIn(issueIds).stream()
                .filter(issue -> status.contains(issue.getStatus()))
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }
}
