package com.samoonpride.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeRequest {
    private String lineUserId;
    private int issueId;
}
