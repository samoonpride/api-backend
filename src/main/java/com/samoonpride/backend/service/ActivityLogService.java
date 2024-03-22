package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.LogDto;
import com.samoonpride.backend.enums.ActivityLogAction;

import java.util.List;

public interface ActivityLogService {
    List<LogDto> getAllLogs();

    void logAction(ActivityLogAction activityLogAction, String username, String message);
}
