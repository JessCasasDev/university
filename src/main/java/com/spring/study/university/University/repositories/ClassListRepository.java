package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.ClassList;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClassListRepository extends CrudRepository<ClassList, UUID> {
}
