package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GradeValidations {
  private final ConstraintValidations constraintValidations;


  public void validateGradeValue(Float grade) {
    if (grade > 5 || grade < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grade must be between 0 and 5");
    }
  }

  public void validateIfGradeExistsForStudent(Optional<Grade> grade) {
    grade.ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already graded to Student");
    });
  }

  public Grade validateIfGradeExists(Optional<Grade> grade) {
    return grade
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found"));
  }

  public void validateGradeFields(Grade grade) {
    constraintValidations.validateFields(grade);
  }

}
