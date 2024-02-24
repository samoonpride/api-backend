package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.UserDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.enums.UserEnum;
import com.samoonpride.backend.model.Issue;
import com.samoonpride.backend.repository.IssueRepository;
import com.samoonpride.backend.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;

    private final LineUserServiceImpl lineUserService;
    private final MediaServiceImpl multimediaService;
    private final StaffServiceImpl staffService;
    private final ModelMapper modelMapper;

    @Override
    public void createIssue(CreateIssueRequest createIssueRequest) {
        Issue issue = new Issue();
        // set user
        this.setIssueUser(issue, createIssueRequest.getUser());
        // set another field
        issue.setTitle(createIssueRequest.getTitle());
        issue.setThumbnailPath(createIssueRequest.getThumbnailPath());
        issue.setLatitude(createIssueRequest.getLatitude());
        issue.setLongitude(createIssueRequest.getLongitude());
        issueRepository.save(issue);
        // set media
        multimediaService.createMultimedia(issue, createIssueRequest.getMedia());
    }

    @Override
    public void updateIssueStatus(int issueId, UpdateIssueStatusRequest updateIssueStatusRequest) {
        Issue issue = issueRepository.findById(issueId);
        issue.setStatus(updateIssueStatusRequest.getStatus());
        issueRepository.save(issue);
    }

    @Override
    public void reopenIssue(int issueId) {
        Issue issue = issueRepository.findById(issueId);
        if (issue.getStatus() == IssueStatus.COMPLETED) {
            issue.setStatus(IssueStatus.IN_CONSIDERATION);
            issueRepository.save(issue);
        }
        throw new IllegalArgumentException("Issue is not status completed");
    }

    // get latest 10 issues
    @Override
    public List<IssueBubbleDto> getLatestTenIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        List<Issue> issueList = issueRepository.findFirst10ByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(userId, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueBubbleDto> getAllIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        List<Issue> issueList = issueRepository.findAllByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(userId, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueDto> getAllIssues() {
        log.info("get all issues");
        List<Issue> issueList = (List<Issue>) issueRepository.findAll();
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueDto.class))
                .collect(Collectors.toList());
    }

    private void setIssueUser(Issue issue, UserDto userDto) {
        if (userDto.getType() == UserEnum.LINE) {
            issue.setLineUser(lineUserService.findByUserId(userDto.getUserId()));
        } else {
            issue.setStaff(staffService.findStaffByUsername(userDto.getUserId()));
        }
    }
}
