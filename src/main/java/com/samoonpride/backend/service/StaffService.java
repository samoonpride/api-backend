package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;

public interface StaffService {
    LoginDto createStaff(Staff staff);

    LoginDto login(StaffLoginRequest staffLoginRequestDto);
}
