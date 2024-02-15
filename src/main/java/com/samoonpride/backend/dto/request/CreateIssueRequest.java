package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueRequest {
    private UserDto user;
    private String title;
    private float latitude;
    private float longitude;
    private String thumbnailPath;
    private List<MediaDto> media;
}
