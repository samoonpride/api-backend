package com.samoonpride.backend.dto;

import com.samoonpride.backend.enums.StaffEnum;

public record LoginDto (
    String username,
    StaffEnum role,
    String token
) {}
