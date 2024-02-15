package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.IssueBubbleDto;
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
    public Issue createIssue(CreateIssueRequest createIssueRequest) {
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
        return issue;
    }

    private void setIssueUser(Issue issue, UserDto userDto) {
        if (userDto.getType() == UserEnum.LINE) {
            issue.setLineUser(lineUserService.findByUserId(userDto.getUserId()));
        } else {
            issue.setStaff(staffService.findByEmail(userDto.getUserId()));
        }
    }

    public void updateIssueStatus(UpdateIssueStatusRequest updateIssueStatusRequest) {
        Issue issue = issueRepository.findById(updateIssueStatusRequest.getIssueId());
        issue.setStatus(updateIssueStatusRequest.getStatus());
        issueRepository.save(issue);
    }

    // get latest 10 issues
    public List<IssueBubbleDto> getLatestTenIssuesByLineUserAndStatus(String userId, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        List<Issue> issueList = issueRepository.findFirst10ByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(userId, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }

    public List<IssueBubbleDto> getAllIssuesByLineUserAndStatus(String email, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        List<Issue> issueList = issueRepository.findAllByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(email, status);
        return issueList.stream()
                .map(issue -> modelMapper.map(issue, IssueBubbleDto.class))
                .collect(Collectors.toList());
    }
}