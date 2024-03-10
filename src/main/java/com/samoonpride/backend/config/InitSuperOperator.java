package com.samoonpride.backend.config;

import com.samoonpride.backend.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitSuperOperator {
    private final StaffService staffService;
    @Value("${superOperator.username}")
    private String superOperatorUsername;
    @Value("${superOperator.password}")
    private String superOperatorPassword;

    @Bean
    public CommandLineRunner createSuperOperator() {
        return args -> staffService.createSuperOperator(superOperatorUsername, superOperatorPassword);
    }
}
