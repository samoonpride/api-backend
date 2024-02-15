package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.Data;

import java.util.List;

@Data
public class GetIssueByLineUserRequest {
    private List<IssueStatus> status = List.of(IssueStatus.IN_CONSIDERATION, IssueStatus.IN_PROGRESS);
}