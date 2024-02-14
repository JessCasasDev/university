package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.repositories.ProfessorRepository;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ProfessorValidations {

  private final ProfessorRepository professorRepository;
  private final ConstraintValidations constraintValidations;

  public Professor validateIfProfessorExists(UUID uuid) {
    return professorRepository
        .findByUuid(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found"));
  }

  public void validateProfessorNoExist(Long documentNumber){
    professorRepository.findByDocumentNumber(documentNumber).ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    });
  }


  public void validateProfessorRole(Professor professor){
    if (!professor.getRole().getRole().name().equals(RoleEnum.Professor.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public void validateProfessorConstraints(Professor professor){
    constraintValidations.validateFields(professor);
  }
}
