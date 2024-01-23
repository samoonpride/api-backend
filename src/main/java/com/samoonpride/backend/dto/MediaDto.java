package com.samoonpride.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samoonpride.backend.enums.MediaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private MediaEnum type;
    @JsonProperty("message_id")
    private String messageId;
    private String path;
}
