package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.authentication.JwtUtil;
import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.StaffDto;
import com.samoonpride.backend.dto.request.ChangePasswordRequest;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.repository.StaffRepository;
import com.samoonpride.backend.service.StaffService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private AuthenticationManager authenticationManager;
    private StaffRepository staffRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public Staff findStaffByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    public LoginDto createStaff(Staff staff) {
        Staff encodedPasswordStaff = new Staff(
            staff.getUsername(), 
            passwordEncoder.encode(staff.getPassword()), 
            staff.getRole()
        );
        staffRepository.save(encodedPasswordStaff);
        return login(new StaffLoginRequest(staff.getUsername(), staff.getPassword()));
    }

    public LoginDto login(StaffLoginRequest staffLoginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    staffLoginRequest.getUsername(), 
                    staffLoginRequest.getPassword()
                )
            );
            Staff staff = staffRepository.findByUsername(authentication.getName());
            String token = jwtUtil.createToken(staff);
            LoginDto loginDto = new LoginDto(staff.getUsername(), staff.getRole(), token);
            return loginDto;

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
            LoginDto loginDto = new LoginDto(staff.getUsername(), staff.getRole(), token);
            return loginDto;

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
    public void deleteStaff(int staffId) {
        staffRepository.findById(staffId).ifPresent((staff) -> {
            staffRepository.delete(staff);
        });
    }

}
