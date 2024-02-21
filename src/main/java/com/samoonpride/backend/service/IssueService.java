package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.IssueStatus;

import java.util.List;

public interface IssueService {
    void createIssue(CreateIssueRequest createIssueRequest);

    void updateIssueStatus(int issueId, UpdateIssueStatusRequest updateIssueStatusRequest);

    void reopenIssue(int issueId);

    List<IssueBubbleDto> getLatestTenIssuesByLineUserAndStatus(String userId, List<IssueStatus> status);

    List<IssueBubbleDto> getAllIssuesByLineUserAndStatus(String email, List<IssueStatus> status);

    List<IssueDto> getAllIssues();
}
