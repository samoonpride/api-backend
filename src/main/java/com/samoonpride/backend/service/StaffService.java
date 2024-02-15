package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;

public interface StaffService {
    void createStaff(Staff staff);

    boolean login(StaffLoginRequest staffLoginRequestDto);
}
