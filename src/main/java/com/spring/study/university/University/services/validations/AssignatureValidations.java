package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.AssignatureRepository;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AssignatureValidations {

  private final AssignatureRepository assignatureRepository;
  private final ConstraintValidations constraintValidations;

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

    constraintValidations.validateFields(assignature);
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

  public void validateAssignaturePrerequisiteValid(Assignature assignature, Student student) {
    if (!assignature.getPrerequisites().isEmpty()) {

      List<Assignature> prerequsites = new ArrayList<>(assignature.getPrerequisites()
          .stream().map(prerequisite -> prerequisite.getAssignature()).toList());

      List<Grade> studentAssignatures = student
          .getGrades()
          .stream()
          .filter(grade -> prerequsites.contains(grade.getAssignature())).toList();

      if (studentAssignatures.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prerequisite required");
      }

      if (studentAssignatures.stream().noneMatch(grade -> grade.getGrade() >= Float.valueOf(3))) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prerequisite needs at least 3.0");
      }
    }
  }
}
