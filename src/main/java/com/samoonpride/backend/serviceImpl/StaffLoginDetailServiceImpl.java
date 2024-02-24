package com.samoonpride.backend.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.repository.StaffRepository;
import com.samoonpride.backend.service.StaffLoginDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StaffLoginDetailServiceImpl implements StaffLoginDetailService {
    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByUsername(username);
        List<String> roles = new ArrayList<>();
        roles.add(staff.getRole().toString());
        UserDetails userDetails = User.builder()
                                    .username(staff.getUsername())
                                    .password(staff.getPassword())
                                    .roles(roles.toArray(new String[0]))
                                    .build();
        return userDetails;
    }
    
}
