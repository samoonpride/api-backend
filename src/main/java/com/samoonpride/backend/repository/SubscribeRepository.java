package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.Subscribe;
import org.springframework.data.repository.CrudRepository;

public interface SubscribeRepository extends CrudRepository<Subscribe, Long> {
    boolean existsByLineUser_UserIdAndIssueId(String lineUserId, int issueId);
    void deleteByLineUser_UserIdAndIssueId(String lineUserId, int issueId);

}
