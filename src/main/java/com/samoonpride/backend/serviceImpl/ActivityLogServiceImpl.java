package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.config.ModelMapperConfig;
import com.samoonpride.backend.dto.LogDto;
import com.samoonpride.backend.enums.ActivityLogAction;
import com.samoonpride.backend.model.ActivityLog;
import com.samoonpride.backend.repository.ActivityLogRepository;
import com.samoonpride.backend.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final ModelMapperConfig modelMapperConfig;

    @Override
    public List<LogDto> getAllLogs() {
        return activityLogRepository.findAll().stream()
                .map(activityLog -> modelMapperConfig.modelMapper().map(activityLog, LogDto.class))
                .toList();
    }
    @Override
    public void logAction(ActivityLogAction activityLogAction, String username, String message) {
        activityLogRepository.save(new ActivityLog(activityLogAction.getAction(), username, message));
    }

}
