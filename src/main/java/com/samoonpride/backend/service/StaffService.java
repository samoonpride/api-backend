package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.StaffLoginDto;
import com.samoonpride.backend.model.Staff;

public interface StaffService {
    void createStaff(Staff staff);
    boolean login(StaffLoginDto staffLoginDto);
}
