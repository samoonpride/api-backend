package com.samoonpride.backend.dto;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private int issueId;
    private Integer duplicateIssueId;
    private String title;
    private float latitude;
    private float longitude;
    private String thumbnailPath;
    private IssueStatus status;
}
