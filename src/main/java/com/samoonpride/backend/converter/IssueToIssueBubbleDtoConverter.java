package com.samoonpride.backend.converter;

import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.model.Issue;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

@Log4j2
public class IssueToIssueBubbleDtoConverter implements Converter<Issue, IssueBubbleDto> {
    private final String lineUserId;

    public IssueToIssueBubbleDtoConverter(String lineUserId) {
        this.lineUserId = lineUserId;
    }

    @Override
    public IssueBubbleDto convert(MappingContext<Issue, IssueBubbleDto> context) {
        Issue source = context.getSource();
        IssueBubbleDto destination = context.getDestination();
        if (destination == null) {
            destination = new IssueBubbleDto();
        }

        if (lineUserId != null && !source.getLineUser().getUserId().equals(lineUserId)) {
            destination.setSubscribed(
                    source.getSubscribes().stream().anyMatch(
                            subscriber -> subscriber.getLineUser().getUserId().equals(lineUserId)
                    )
            );
        }
        destination.setIssueId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setThumbnailPath(source.getThumbnailPath());
        destination.setStatus(source.getStatus());
        return destination;
    }
}
