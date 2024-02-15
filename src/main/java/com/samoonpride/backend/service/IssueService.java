package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;

public interface IssueService {
    void createIssue(CreateIssueRequest createIssueRequest);
    void updateIssueStatus(int issueId, UpdateIssueStatusRequest updateIssueStatusRequest);
    void reopenIssue(int issueId);
}
