package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.LineUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineUserRepository extends CrudRepository<LineUser, Long> {
    LineUser findByUserId(String userId);
}
