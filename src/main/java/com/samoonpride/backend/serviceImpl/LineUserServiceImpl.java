package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.request.CreateLineUserRequest;
import com.samoonpride.backend.model.LineUser;
import com.samoonpride.backend.model.Issue;
import com.samoonpride.backend.repository.LineUserRepository;
import com.samoonpride.backend.service.LineUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LineUserServiceImpl implements LineUserService {
    private LineUserRepository lineUserRepository;
    private ModelMapper modelMapper;

    @Override
    public void createLineUser(CreateLineUserRequest createLineUserRequest) {
        LineUser lineUser = modelMapper.map(createLineUserRequest, LineUser.class);
        if (lineUserRepository.findByUserId(lineUser.getUserId()) != null) {
            return;
        }
        lineUserRepository.save(lineUser);
    }

    @Override
    public LineUser findByUserId(String lineUserId) {
        return lineUserRepository.findByUserId(lineUserId);
    }
}
