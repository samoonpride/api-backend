package com.samoonpride.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.dto.UserDto;
import com.samoonpride.backend.enums.UserEnum;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportRequest {
    private UserDto user;
    private String title;
    private float latitude;
    private float longitude;
    private List<MediaDto> media;
}
