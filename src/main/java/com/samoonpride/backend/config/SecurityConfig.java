package com.samoonpride.backend.config;

import com.samoonpride.backend.authentication.Http401UnauthorizedEntryPoint;
import com.samoonpride.backend.authentication.JwtAuthorizationFilter;
import com.samoonpride.backend.serviceImpl.StaffLoginDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final StaffLoginDetailServiceImpl staffLoginDetailService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Value("${security.enable}")
    private boolean isEnable = false;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(staffLoginDetailService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (isEnable) {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests((authorizeHttpRequests) ->
                            authorizeHttpRequests
                                    .requestMatchers("/api/staff/**").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .sessionManagement((management) ->
                            management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling((exceptionHandling) ->
                            exceptionHandling.authenticationEntryPoint(new Http401UnauthorizedEntryPoint())
                    );
        } else {
            log.warn("Security is disabled");
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests((authorizeHttpRequests) ->
                            authorizeHttpRequests.anyRequest().permitAll()
                    );
        }

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
