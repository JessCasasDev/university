package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.DTO.GradeDTO;
import com.spring.study.university.University.domain.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {

  Optional<Student> findByStudentNumber(Long studentNumber);

  @Query(value = "SELECT g.uuid as uuid, g.grade as grade, g.assignature as assignature, g.student as student " +
      "FROM students s join grades g on g.student = s.uuid " +
      "WHERE s.student_number = :studentNumber",
      nativeQuery = true)
  List<GradeDTO> getGradesByStudentNumber(@Param("studentNumber") Long studentNumber);
}
