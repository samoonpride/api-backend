package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.StaffLoginDto;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.serviceImpl.StaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffServiceImpl staffService;

    // Create a new staff
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStaff(@RequestBody Staff staff) {
        staffService.createStaff(staff);
    }

    // Login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody StaffLoginDto staffLoginDto) {
        if (staffService.login(staffLoginDto)) {
            return "Login successful";
        } else {
            return "Login failed";
        }
    }
}
