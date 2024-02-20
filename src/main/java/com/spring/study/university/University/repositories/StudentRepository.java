package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {

  Optional<Student> findByStudentNumber(Long studentNumber);
}
