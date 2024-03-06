package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIssueStatusRequest {
    private IssueStatus status;
}
