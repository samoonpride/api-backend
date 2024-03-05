package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.Subscribe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscribeRepository extends CrudRepository<Subscribe, Integer> {
    boolean existsByLineUser_UserIdAndIssueId(String lineUserId, int issueId);

    void deleteByLineUser_UserIdAndIssueId(String lineUserId, int issueId);

    List<Subscribe> findByLineUser_UserId(String lineUserId);
}
