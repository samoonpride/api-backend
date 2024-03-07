package com.samoonpride.backend.converter;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.model.Video;
import com.samoonpride.backend.utils.MediaUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class MediaDtoToVideoConverter implements Converter<MediaDto, Video> {
    private final String userId;

    public MediaDtoToVideoConverter(String userId) {
        this.userId = userId;
    }

    @Override
    public Video convert(MappingContext<MediaDto, Video> mappingContext) {
        MediaDto source = mappingContext.getSource();
        Video destination = mappingContext.getDestination();
        if (destination == null) {
            destination = new Video();
        }
        destination.setMessageId(source.getMessageId());
        destination.setPath(
                MediaUtils.saveVideo(
                        userId,
                        source.getMessageId()
                )
        );
        return destination;
    }
}
