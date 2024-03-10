package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.dto.request.ChangePasswordRequest;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;
import io.jsonwebtoken.Claims;

import java.util.List;

public interface StaffService {
    Staff findStaffByUsername(String username);

    LoginDto createStaff(Staff staff, Claims claims);

    LoginDto login(StaffLoginRequest staffLoginRequestDto);

    LoginDto changePassword(ChangePasswordRequest changePasswordRequest);

    List<StaffDto> getStaffs();

    void deleteStaff(int id, Claims claims);

    void createSuperOperator(String username, String password);
}
