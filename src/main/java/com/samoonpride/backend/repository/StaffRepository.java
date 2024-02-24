package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Staff findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);
}
