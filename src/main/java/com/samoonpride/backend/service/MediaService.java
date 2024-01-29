package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.model.Issue;

import java.util.List;

public interface MediaService {
    void createMultimedia(Issue issue, List<MediaDto> mediaDto);
}
