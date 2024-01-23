package com.samoonpride.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StaffLoginRequest {
    private String username;
    private String password;
}
