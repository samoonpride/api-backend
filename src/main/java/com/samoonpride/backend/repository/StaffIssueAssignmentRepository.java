package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.StaffIssueAssignment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface StaffIssueAssignmentRepository extends CrudRepository<StaffIssueAssignment, Long> {
    Set<StaffIssueAssignment> findByIssueIdIn(List<Integer> issueId);
}
