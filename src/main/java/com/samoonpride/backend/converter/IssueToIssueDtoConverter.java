package com.samoonpride.backend.converter;

import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.model.Issue;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

@Log4j2
public class IssueToIssueDtoConverter implements Converter<Issue, IssueDto> {
    @Override
    public IssueDto convert(MappingContext<Issue, IssueDto> context) {
        Issue source = context.getSource();
        IssueDto destination = context.getDestination();
        if (destination == null) {
            destination = new IssueDto();
        }
        destination.setIssueId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setLatitude(source.getLatitude());
        destination.setLongitude(source.getLongitude());
        destination.setThumbnailPath(source.getThumbnailPath());
        if (source.getDuplicateIssue() != null) {
            destination.setDuplicateIssueId(source.getDuplicateIssue().getId());
            destination.setStatus(source.getDuplicateIssue().getStatus());
        } else {
            destination.setDuplicateIssueId(null);
            destination.setStatus(source.getStatus());
        }
        return destination;
    }
}
