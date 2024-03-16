package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.enums.ActivityLogAction;
import com.samoonpride.backend.model.ActivityLog;
import com.samoonpride.backend.repository.ActivityLogRepository;
import com.samoonpride.backend.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepository activityLogRepository;

    @Override
    public void logAction(ActivityLogAction activityLogAction, String message) {
        activityLogRepository.save(new ActivityLog(activityLogAction.getAction(), message));
    }

}
