package com.samoonpride.backend.service;

import com.samoonpride.backend.enums.ActivityLogAction;

public interface ActivityLogService {
    void logAction(ActivityLogAction activityLogAction, String message);
}
