package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.AssignatureRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AssignatureValidations {

  private final AssignatureRepository assignatureRepository;
  private Validator validator;

  public Assignature validateIfAssignatureExists(UUID uuid) {
    return assignatureRepository
        .findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
  }

  public void validateAssignatureInformationCreation(Assignature assignature) {
    Optional<Assignature> assignatureSaved = assignatureRepository
        .findByNameAndCredits(assignature.getName(), assignature.getCredits());

    assignatureSaved.ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    });

    Set<ConstraintViolation<Assignature>> violations = validator.validate(assignature);

    if (!violations.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (ConstraintViolation<Assignature> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred: " + sb.toString());
    }
  }

  public void validateAssignatureTakenByProfessor(Assignature assignature, Professor professor) {
    if (assignature.getProfessor() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already taken");
    }

    assignatureRepository
        .findByProfessor(professor)
        .stream()
        .filter(assignature1 -> assignature1.equals(assignature))
        .findAny()
        .ifPresent(s -> {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already taken");
        });
  }

  public void validatePrerequisite(Assignature assignature, List<UUID> prerequisites) {
    assignature
        .getPrerequisites().stream()
        .map(prerequisite -> prerequisite.getPrerequisite().getUuid())
        .filter(prerequisites::contains)
        .anyMatch(s -> {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prerequisite already exists");
        });

    prerequisites
        .stream()
        .filter(assignature.getUuid()::equals)
        .anyMatch(s -> {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature cannot be part of prerequisites");
        });
  }
}
