package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.request.CreateLineUserRequest;
import com.samoonpride.backend.enums.ActivityLogAction;
import com.samoonpride.backend.model.LineUser;
import com.samoonpride.backend.repository.LineUserRepository;
import com.samoonpride.backend.service.LineUserService;
import com.samoonpride.backend.utils.LogMessageFormatter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LineUserServiceImpl implements LineUserService {
    private LineUserRepository lineUserRepository;
    private ActivityLogServiceImpl activityLogService;
    private ModelMapper modelMapper;

    @Override
    public void createLineUser(CreateLineUserRequest createLineUserRequest) {
        LineUser lineUser = modelMapper.map(createLineUserRequest, LineUser.class);
        if (lineUserRepository.findByUserId(lineUser.getUserId()) != null) {
            return;
        }

        activityLogService.logAction(
                ActivityLogAction.USER_CREATED,
                LogMessageFormatter.formatUserCreated(lineUser)
        );

        lineUserRepository.save(lineUser);
    }

    @Override
    public LineUser findByUserId(String lineUserId) {
        return lineUserRepository.findByUserId(lineUserId);
    }
}
