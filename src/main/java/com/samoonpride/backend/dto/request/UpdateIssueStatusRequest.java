package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateIssueStatusRequest {
    private int issueId;
    private IssueStatus status;
}
