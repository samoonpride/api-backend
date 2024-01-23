package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.serviceImpl.StaffServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffServiceImpl staffService;

    // Create a new staff
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStaff(@RequestBody Staff staff) {
        staffService.createStaff(staff);
    }

    // Login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody StaffLoginRequest staffLoginRequestDto) {
        if (staffService.login(staffLoginRequestDto)) {
            return "Login successful";
        } else {
            return "Login failed";
        }
    }
}
