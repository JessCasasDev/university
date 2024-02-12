package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ProfessorValidations {

  private final ProfessorRepository professorRepository;


  public Professor validateIfProfessorExists(UUID uuid) {
    return professorRepository
        .findByUuid(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found"));
  }


}
