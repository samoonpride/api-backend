package com.samoonpride.backend.converter;

import com.samoonpride.backend.dto.MediaDto;
import com.samoonpride.backend.model.Image;
import com.samoonpride.backend.utils.MediaUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class MediaDtoToImageConverter implements Converter<MediaDto, Image> {
    private final String userId;

    public MediaDtoToImageConverter(String userId) {
        this.userId = userId;
    }

    @Override
    public Image convert(MappingContext<MediaDto, Image> mappingContext) {
        MediaDto source = mappingContext.getSource();
        Image destination = mappingContext.getDestination();
        if (destination == null) {
            destination = new Image();
        }
        destination.setMessageId(source.getMessageId());
        destination.setPath(
                MediaUtils.saveImage(
                        userId,
                        source.getMessageId()
                )
        );
        return destination;
    }
}
