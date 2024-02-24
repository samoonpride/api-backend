package com.samoonpride.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface StaffLoginDetailService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
