package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.converter.MediaDtoToImageConverter;
import com.samoonpride.backend.converter.MediaDtoToVideoConverter;
import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.enums.MediaEnum;
import com.samoonpride.backend.model.Image;
import com.samoonpride.backend.model.Issue;
import com.samoonpride.backend.model.Media;
import com.samoonpride.backend.model.Video;
import com.samoonpride.backend.repository.MediaRepository;
import com.samoonpride.backend.service.MediaService;
import com.samoonpride.backend.utils.ThumbnailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;

    @Override
    public void createMultimedia(Issue issue, List<MediaDto> mediaDto) {
        String userId = issue.getLineUser().getUserId();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new MediaDtoToImageConverter(userId));
        modelMapper.addConverter(new MediaDtoToVideoConverter(userId));

        for (MediaDto dto : mediaDto) {
            Media media;
            if (dto.getType() == MediaEnum.IMAGE) {
                log.info("Saving image");
                media = modelMapper.map(dto, Image.class);
            } else {
                log.info("Saving video");
                media = modelMapper.map(dto, Video.class);
            }
            media.setIssue(issue);
            // Set thumbnail
            if (issue.getThumbnailPath() == null) {
                log.info("Setting thumbnail");
                Path mediaPath = Path.of("public").resolve(media.getPath());
                issue.setThumbnailPath(ThumbnailUtils.createThumbnail(mediaPath.toFile()));
            }

            mediaRepository.save(media);
        }
    }
}
