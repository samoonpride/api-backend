package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.model.Report;

import java.util.List;

public interface MediaService {
    void createMultimedia(Report report, List<MediaDto> mediaDto);
}
