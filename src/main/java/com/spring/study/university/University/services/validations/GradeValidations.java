package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.GradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GradeValidations {

  private GradeRepository gradeRepository;

  public void validateGradeValue(Float grade) {
    if (grade > 5 || grade < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grade must be between 0 and 5");
    }
  }

  public void validateIfGradeExistsForStudent(Student student, Assignature assignature) {
    if (gradeRepository.findByStudentAndAssignature(student, assignature).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already graded to Student");
    }
  }

  public Grade validateIfGradeExists(UUID uuid) {
    return gradeRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
  }
}