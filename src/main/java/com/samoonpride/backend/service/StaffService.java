package com.samoonpride.backend.service;

import java.util.List;

import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.dto.request.ChangePasswordRequest;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;

public interface StaffService {
    Staff findStaffByUsername(String username);

    LoginDto createStaff(Staff staff);

    LoginDto login(StaffLoginRequest staffLoginRequestDto);

    LoginDto changePassword(ChangePasswordRequest changePasswordRequest);

    List<StaffDto> getStaffs();

}
