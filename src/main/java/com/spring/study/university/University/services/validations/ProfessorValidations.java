package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ProfessorValidations {

  private final ConstraintValidations constraintValidations;

  public Professor validateIfProfessorExists(Optional<Professor> professor) {
    return professor
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found"));
  }

  public void validateProfessorNoExist(Optional<Professor> professor) {
    professor.ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor already exists");
    });
  }


  public void validateProfessorRole(Professor professor) {
    if (!professor.getRole().getRole().name().equals(RoleEnum.Professor.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Role");
    }
  }

  public void validateProfessorConstraints(Professor professor) {
    constraintValidations.validateFields(professor);
  }
}
