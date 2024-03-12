package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.enums.StaffEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApproveRegistrationRequest {
    private String username;
    private StaffEnum role;
}
