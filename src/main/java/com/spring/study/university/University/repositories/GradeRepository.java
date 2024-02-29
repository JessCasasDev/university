package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradeRepository extends CrudRepository<Grade, UUID> {

  Optional<Grade> findByStudentAndAssignature(Student student, Assignature assignature);
}
