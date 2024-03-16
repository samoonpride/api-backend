package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.ActivityLog;
import org.springframework.data.repository.CrudRepository;

public interface ActivityLogRepository extends CrudRepository<ActivityLog, Integer> {
}
