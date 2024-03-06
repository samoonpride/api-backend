package com.samoonpride.backend.dto;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private int issueId;
    private Integer duplicateIssueId;
    private List<Integer> assigneeIds;
    private String title;
    private float latitude;
    private float longitude;
    private String thumbnailPath;
    private IssueStatus status;
}
