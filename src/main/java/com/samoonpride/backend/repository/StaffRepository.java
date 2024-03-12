package com.samoonpride.backend.repository;

import com.samoonpride.backend.enums.StaffEnum;
import com.samoonpride.backend.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Staff findByUsername(String username);

    List<Staff> findByIdIn(List<Integer> id);

    boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByRole(StaffEnum role);

    boolean existsByUsername(String username);
}
