package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.ActivityLog;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityLogRepository extends CrudRepository<ActivityLog, Integer> {
    @NonNull List<ActivityLog> findAll();
}
