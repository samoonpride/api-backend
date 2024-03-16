package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIssueRequest {
    private int issueId;
    private Integer duplicateIssueId;
    private List<Integer> assigneeIds;
    private String title;
    private float latitude;
    private float longitude;
    private IssueStatus status;

    @Override
    public String toString() {
        return "{" +
                "id=" + issueId +
                ", title='" + title + '\'' +
                ", duplicateIssueId=" + duplicateIssueId +
                ", assigneeIds=" + assigneeIds +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status=" + status +
                '}';
    }
}
