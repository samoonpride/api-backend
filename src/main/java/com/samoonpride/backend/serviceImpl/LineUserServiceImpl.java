package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.model.LineUser;
import com.samoonpride.backend.repository.LineUserRepository;
import com.samoonpride.backend.service.LineUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LineUserServiceImpl implements LineUserService {
    private LineUserRepository lineUserRepository;

    @Override
    public LineUser createLineUser(LineUser lineUser) {
        LineUser existingUser = lineUserRepository.findByUserId(lineUser.getUserId());
        return Objects.requireNonNullElseGet(existingUser, () -> lineUserRepository.save(lineUser));
    }
}
