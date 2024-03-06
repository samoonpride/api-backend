package com.samoonpride.backend.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.model.Staff;

public class StaffToStaffDtoConverter implements Converter<Staff, StaffDto> {

    @Override
    public StaffDto convert(MappingContext<Staff, StaffDto> context) {
        Staff source = context.getSource();
        StaffDto staffDto = new StaffDto(
            source.getId(), 
            source.getUsername(), 
            source.getRole(), 
            source.getIssues(), 
            source.getCreatedDate(), 
            source.getLastModifiedDate()
        );
        return staffDto;
    }
    
}
