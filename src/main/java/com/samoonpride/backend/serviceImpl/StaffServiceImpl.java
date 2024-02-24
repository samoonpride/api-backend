package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.authentication.JwtUtil;
import com.samoonpride.backend.dto.LoginDto;
import com.samoonpride.backend.dto.request.StaffLoginRequest;
import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.repository.StaffRepository;
import com.samoonpride.backend.service.StaffService;
import lombok.AllArgsConstructor;

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

    public Staff findStaffByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    public LoginDto createStaff(Staff staff) {
        Staff encodedPasswordStaff = new Staff(
            staff.getEmail(), 
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
            LoginDto loginDto = new LoginDto(staff.getUsername(), token);
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
}
