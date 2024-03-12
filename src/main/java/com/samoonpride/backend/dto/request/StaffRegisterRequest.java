package com.samoonpride.backend.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffRegisterRequest {
    private String username;
    private String password;
}
