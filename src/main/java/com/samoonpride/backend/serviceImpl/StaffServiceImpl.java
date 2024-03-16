package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.authentication.JwtUtil;
import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.dto.request.*;
import com.samoonpride.backend.enums.ActivityLogAction;
import com.samoonpride.backend.enums.StaffEnum;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.repository.StaffRepository;
import com.samoonpride.backend.service.StaffService;
import com.samoonpride.backend.utils.LogMessageFormatter;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private AuthenticationManager authenticationManager;
    private StaffRepository staffRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private ActivityLogServiceImpl activityLogService;

    @SneakyThrows
    private static String Md5PasswordEncode(String password) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    @Override
    public Staff findStaffByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    @Override
    public LoginDto createStaff(Staff staff, Claims claims) {
        String role = (String) claims.get("role");
        if (staff == null || StaffEnum.fromString(role).hasLowerPriorityThan(staff.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to create this staff"
            );
        }
        Staff encodedPasswordStaff = new Staff(
                staff.getUsername(),
                passwordEncoder.encode(staff.getPassword()),
                staff.getRole()
        );
        staffRepository.save(encodedPasswordStaff);
        return login(new StaffLoginRequest(staff.getUsername(), staff.getPassword()));
    }

    @Override
    public LoginDto login(StaffLoginRequest staffLoginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            staffLoginRequest.getUsername(),
                            staffLoginRequest.getPassword()
                    )
            );
            Staff staff = staffRepository.findByUsername(authentication.getName());
            if (staff.isPending()) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Your account is not approved yet"
                );
            }
            String token = jwtUtil.createToken(staff);

            activityLogService.logAction(
                    ActivityLogAction.STAFF_LOGIN,
                    LogMessageFormatter.formatStaffLogin(staff)
            );

            return new LoginDto(staff.getUsername(), staff.getRole(), token);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid username or password",
                    e
            );

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void register(StaffRegisterRequest staffRegisterRequest) {
        if (staffRepository.existsByUsername(staffRegisterRequest.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Username is already taken"
            );
        }
        Staff staff = new Staff(
                staffRegisterRequest.getUsername(),
                passwordEncoder.encode(staffRegisterRequest.getPassword()),
                StaffEnum.PENDING
        );

        activityLogService.logAction(
                ActivityLogAction.STAFF_REGISTER,
                LogMessageFormatter.formatStaffRegister(staff)
        );

        staffRepository.save(staff);
    }

    @Override
    public void approveRegistration(ApproveRegistrationRequest approveRegistrationRequest, Claims claims) {
        Staff staff = findStaffByUsername(approveRegistrationRequest.getUsername());
        StaffEnum setRole = approveRegistrationRequest.getRole();
        String role = (String) claims.get("role");
        if (staff == null || StaffEnum.fromString(role).hasLowerPriorityThan(staff.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to approve this staff"
            );
        }
        staff.setRole(setRole);

        activityLogService.logAction(
                ActivityLogAction.STAFF_APPROVAL,
                LogMessageFormatter.formatStaffApproval(
                        claims.get("username", String.class),
                        staff
                )
        );

        staffRepository.save(staff);
    }

    @Override
    public void declineRegistration(DeclineRegistrationRequest declineRegistrationRequest, Claims claims) {
        Staff staff = findStaffByUsername(declineRegistrationRequest.getUsername());
        String role = (String) claims.get("role");
        if (staff == null || StaffEnum.fromString(role).hasLowerPriorityThan(staff.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to decline this staff"
            );
        }

        activityLogService.logAction(
                ActivityLogAction.STAFF_DECLINATION,
                LogMessageFormatter.formatStaffDeclination(
                        claims.get("username", String.class),
                        staff
                )
        );

        staffRepository.delete(staff);
    }

    @Override
    public LoginDto changePassword(ChangePasswordRequest changePasswordRequest) {
        try {
            Staff staff = staffRepository.findByUsername(changePasswordRequest.username());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            changePasswordRequest.username(),
                            changePasswordRequest.currentPassword()
                    )
            );

            String encodedNewPassword = passwordEncoder.encode(changePasswordRequest.newPassword());
            staff.setPassword(encodedNewPassword);
            staffRepository.save(staff);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            changePasswordRequest.username(),
                            changePasswordRequest.newPassword()
                    )
            );

            String token = jwtUtil.createToken(staff);

            activityLogService.logAction(
                    ActivityLogAction.STAFF_CHANGE_PASSWORD,
                    LogMessageFormatter.formatStaffChangePassword(staff)
            );

            return new LoginDto(staff.getUsername(), staff.getRole(), token);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid password",
                    e
            );

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<StaffDto> getStaffs() {
        List<StaffDto> staffs = new ArrayList<>();
        staffRepository.findAll().forEach((staff) -> {
            staffs.add(modelMapper.map(staff, StaffDto.class));
        });

        return staffs;
    }

    @Override
    public void deleteStaff(int id, Claims claims) {
        Staff staff = staffRepository.findById(id).orElse(null);
        String role = (String) claims.get("role");
        if (staff == null || StaffEnum.fromString(role).hasLowerPriorityThan(staff.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to delete this staff"
            );
        }

        activityLogService.logAction(
                ActivityLogAction.STAFF_DELETED,
                LogMessageFormatter.formatStaffDeleted(
                        claims.get("username", String.class),
                        staff
                )
        );

        staffRepository.delete(staff);
    }

    @Override
    public void createSuperOperator(String username, String password) {
        if (staffRepository.existsByRole(StaffEnum.SUPER_OPERATOR)) {
            log.info("Super operator already exists");
            return;
        }
        Staff staff = new Staff(
                username,
                passwordEncoder.encode(Md5PasswordEncode(password)),
                StaffEnum.SUPER_OPERATOR
        );
        staffRepository.save(staff);
        log.info("Super operator created");
    }
}
