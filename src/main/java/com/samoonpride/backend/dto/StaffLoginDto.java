package com.samoonpride.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StaffLoginDto {
    private String username;
    private String password;
}
