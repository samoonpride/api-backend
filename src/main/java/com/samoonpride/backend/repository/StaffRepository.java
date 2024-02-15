package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Long> {
    Optional<Staff> findByUsernameOrEmail(String username, String email);

    Staff findByEmail(String email);

    boolean existsByUsernameAndPassword(String username, String password);
}
