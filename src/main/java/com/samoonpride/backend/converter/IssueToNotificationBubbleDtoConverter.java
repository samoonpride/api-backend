package com.samoonpride.backend.converter;

import com.samoonpride.backend.dto.NotificationBubbleDto;
import com.samoonpride.backend.model.Issue;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;

public class IssueToNotificationBubbleDtoConverter implements Converter<Issue, NotificationBubbleDto> {
    private final List<String> lineUserIdList;
    private final String lineUserId;

    public IssueToNotificationBubbleDtoConverter(String lineUserId) {
        this.lineUserId = lineUserId;
        this.lineUserIdList = null;
    }

    public IssueToNotificationBubbleDtoConverter(List<String> lineUserIdList) {
        this.lineUserId = null;
        this.lineUserIdList = lineUserIdList;
    }

    @Override
    public NotificationBubbleDto convert(MappingContext<Issue, NotificationBubbleDto> context) {
        if (lineUserIdList != null) {
            return convertWithLineUserIdList(context);
        }
        return convertWithLineUserId(context);
    }

    // This means this lineUserId is created or duplicated this issue
    public NotificationBubbleDto convertWithLineUserId(MappingContext<Issue, NotificationBubbleDto> context) {
        Issue source = context.getSource();
        NotificationBubbleDto destination = context.getDestination();
        if (destination == null) {
            destination = new NotificationBubbleDto();
        }
        assert lineUserId != null;
        destination.setLineUserIds(List.of(lineUserId));
        destination.setTitle(source.getTitle());
        destination.setThumbnailPath(source.getThumbnailPath());
        if (source.getDuplicateIssue() != null) {
            destination.setStatus(source.getDuplicateIssue().getStatus());
        } else {
            destination.setStatus(source.getStatus());
        }
        destination.setSubscribed(false);
        return destination;
    }

    // This means this lineUserIdList is subscribed to this issue
    public NotificationBubbleDto convertWithLineUserIdList(MappingContext<Issue, NotificationBubbleDto> context) {
        Issue source = context.getSource();
        NotificationBubbleDto destination = context.getDestination();
        if (destination == null) {
            destination = new NotificationBubbleDto();
        }
        destination.setLineUserIds(lineUserIdList);
        destination.setTitle(source.getTitle());
        destination.setThumbnailPath(source.getThumbnailPath());
        destination.setStatus(source.getStatus());
        destination.setSubscribed(true);
        return destination;
    }
}
