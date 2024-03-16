package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.NotificationBubbleDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.model.Issue;
import io.jsonwebtoken.Claims;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IssueService {
    void createIssue(CreateIssueRequest createIssueRequest);

    void updateIssueStatus(int issueId, UpdateIssueStatusRequest updateIssueStatusRequest);

    void reopenIssue(int issueId, Claims claims);

    @Transactional
    void updateIssue(int issueId, UpdateIssueRequest updateIssueRequest, MultipartFile media, Claims claims);

    List<NotificationBubbleDto> createNotificationBubbleDtoList(Issue issue);

    List<IssueBubbleDto> getLatestTenIssuesByLineUserAndStatus(String userId, List<IssueStatus> status);

    List<IssueBubbleDto> getAllIssuesByLineUserAndStatus(String userId, List<IssueStatus> status);

    List<IssueBubbleDto> getDistinctIssuesByLineUserAndStatus(String userId, List<IssueStatus> status);

    List<IssueDto> getAllIssues();

    List<IssueBubbleDto> getSubscribedIssuesByLineUserAndStatus(String userId, List<IssueStatus> status);
}
