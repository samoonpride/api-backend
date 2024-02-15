package com.samoonpride.backend.dto;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueBubbleDto {
    private String title;
    private String thumbnailPath;
    private IssueStatus status;
}
