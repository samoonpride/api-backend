package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.dto.request.ChangePasswordRequest;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.serviceImpl.StaffServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    
    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public LoginDto changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return staffService.changePassword(changePasswordRequest);
    }

    @GetMapping()
    public List<StaffDto> getStaffs() {
        return staffService.getStaffs();
    }

    @DeleteMapping("/{id}")
    public void deleteStaff(@PathVariable int id) {
        staffService.deleteStaff(id);
    }
    
}
