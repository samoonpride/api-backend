package com.samoonpride.backend.repository;

import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.model.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer> {
    Issue findById(int issueId);

    List<Issue> findFirst10ByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(String userId, List<IssueStatus> status);

    List<Issue> findAllByLineUser_UserIdAndStatusInOrderByCreatedDateDesc(String userId, List<IssueStatus> status);
}
