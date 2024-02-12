package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Prerequisite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrerequisiteRepository extends CrudRepository<Prerequisite, UUID> {
}
