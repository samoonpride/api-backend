package com.samoonpride.backend.dto;

import com.samoonpride.backend.enums.IssueStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NotificationBubbleDto {
    private List<String> lineUserIds;
    private String title;
    private String thumbnailPath;
    private IssueStatus status;
    private boolean subscribed;
}
