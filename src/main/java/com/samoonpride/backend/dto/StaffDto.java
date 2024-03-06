package com.samoonpride.backend.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.samoonpride.backend.enums.StaffEnum;
import com.samoonpride.backend.model.Issue;

public record StaffDto(
    int id,
    String username,
    StaffEnum role,
    Set<Issue> issues,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) { }
