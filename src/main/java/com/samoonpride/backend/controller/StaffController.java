package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.serviceImpl.StaffServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffServiceImpl staffService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginDto login(@RequestBody StaffLoginRequest staffLoginRequest) {
        return staffService.login(staffLoginRequest);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginDto create(@RequestBody Staff staff) {
        return staffService.createStaff(staff);
    }
    

}
