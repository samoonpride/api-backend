package com.samoonpride.backend.controller;

import com.samoonpride.backend.authentication.JwtUtil;
import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.dto.request.*;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.serviceImpl.StaffServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffServiceImpl staffService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginDto login(@RequestBody StaffLoginRequest staffLoginRequest) {
        return staffService.login(staffLoginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody StaffRegisterRequest staffRegisterRequest) {
        staffService.register(staffRegisterRequest);
    }

    @PostMapping("/approve")
    @ResponseStatus(HttpStatus.OK)
    public void approveRegistration(HttpServletRequest request, @RequestBody ApproveRegistrationRequest approveRegistrationRequest) {
        Claims claims = jwtUtil.resolveClaims(request);
        staffService.approveRegistration(approveRegistrationRequest, claims);
    }

    @PostMapping("/decline")
    @ResponseStatus(HttpStatus.OK)
    public void declineRegistration(HttpServletRequest request, @RequestBody DeclineRegistrationRequest declineRegistrationRequest) {
        Claims claims = jwtUtil.resolveClaims(request);
        staffService.declineRegistration(declineRegistrationRequest, claims);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginDto create(HttpServletRequest request, @RequestBody Staff staff) {
        log.info("handling create staff request");
        Claims claims = jwtUtil.resolveClaims(request);
        log.info("claims: {}", claims);
        return staffService.createStaff(staff, claims);
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
    public void deleteStaff(HttpServletRequest request, @PathVariable int id) {
        log.info("handling delete staff request");
        Claims claims = jwtUtil.resolveClaims(request);
        log.info("claims: {}", claims);
        staffService.deleteStaff(id, claims);
    }
}
