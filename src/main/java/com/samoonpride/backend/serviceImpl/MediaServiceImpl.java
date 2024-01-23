package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.enums.MediaEnum;
import com.samoonpride.backend.model.Image;
import com.samoonpride.backend.model.Media;
import com.samoonpride.backend.model.Report;
import com.samoonpride.backend.model.Video;
import com.samoonpride.backend.repository.MediaRepository;
import com.samoonpride.backend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final ModelMapper modelMapper;
    @Override
    public void createMultimedia(Report report, List<MediaDto> mediaDto) {
        for (MediaDto dto : mediaDto) {
            Media media;
            if (dto.getType() == MediaEnum.IMAGE) {
                System.out.printf("Multimedia: %s\n", dto.getMessageId());
                media = modelMapper.map(dto, Image.class);
            } else {
                System.out.printf("Multimedia: %s\n", dto.getMessageId());
                media = modelMapper.map(dto, Video.class);
            }
            media.setReport(report);
            mediaRepository.save(media);
        }
    }
}
