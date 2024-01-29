package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.UserDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.enums.UserEnum;
import com.samoonpride.backend.model.LineUser;
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
        issue.setLatitude(createIssueRequest.getLatitude());
        issue.setLongitude(createIssueRequest.getLongitude());
        issueRepository.save(issue);
        // set media
        multimediaService.createMultimedia(issue, createIssueRequest.getMedia());
        return issue;
    }

    private void setIssueUser(Issue issue, UserDto userDto) {
        if (userDto.getType() == UserEnum.LINE) {
            issue.setLineUser(lineUserService.findByUserId(userDto.getKey()));
        } else {
            issue.setStaff(staffService.findByEmail(userDto.getKey()));
        }
    }

    public void updateIssueStatus(UpdateIssueStatusRequest updateIssueStatusRequest) {
        Issue issue = issueRepository.findById(updateIssueStatusRequest.getIssueId());
        issue.setStatus(updateIssueStatusRequest.getStatus());
        issueRepository.save(issue);
    }

    // get latest 10 issues
    public List<IssueDto> getLatestIssuesByLineUserAndStatusIn(String userId, List<IssueStatus> status) {
        log.info("get latest 10 issues");
        LineUser lineUser = lineUserService.findByUserId(userId);
        List<Issue> issueList = issueRepository.findFirst10ByLineUserAndStatusInOrderByCreatedDateDesc(lineUser, status);
        return issueList.stream()
                         .map(issue -> modelMapper.map(issue, IssueDto.class))
                         .collect(Collectors.toList());
    }
}
