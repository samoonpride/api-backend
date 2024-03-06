package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.model.Issue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    void createMultimedia(Issue issue, List<MediaDto> mediaDto);

    void updateIssueMedia(Issue issue, MultipartFile media);
}
