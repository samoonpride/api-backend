package com.samoonpride.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeBubbleDto {
    private String issueId;
    private IssueBubbleDto issueBubbleDto;
}
