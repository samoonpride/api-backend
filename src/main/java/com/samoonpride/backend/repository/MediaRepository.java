package com.samoonpride.backend.repository;

import com.samoonpride.backend.model.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends CrudRepository<Media, Integer> {
}
