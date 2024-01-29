package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.model.Issue;

public interface IssueService {
    Issue createIssue(CreateIssueRequest createIssueRequest);
    void updateIssueStatus(UpdateIssueStatusRequest updateIssueStatusRequest);
}
