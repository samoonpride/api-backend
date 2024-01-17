package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.model.Staff;
import com.samoonpride.backend.repository.StaffRepository;
import com.samoonpride.backend.service.StaffService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private StaffRepository staffRepository;

    private PasswordEncoder passwordEncoder;

    public void createStaff(Staff staff) {
        // Encode the password
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staffRepository.save(staff);
    }
}
