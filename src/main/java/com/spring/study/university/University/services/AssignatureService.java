package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Prerequisite;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.AssignatureRepository;

import com.spring.study.university.University.services.validations.AssignatureValidations;
import com.spring.study.university.University.services.validations.ProfessorValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AssignatureService {

  private final AssignatureRepository assignatureRepository;
  private final ProfessorValidations professorValidations;
  private final AssignatureValidations assignatureValidations;


  public Assignature createAssignature(Assignature assignature) {
    assignatureValidations.validateAssignatureInformationCreation(assignature);

    return assignatureRepository.save(assignature);
  }

  public Assignature editAssignature(UUID uuid, Assignature assignature) {
    Assignature assignatureSaved = assignatureValidations.validateIfAssignatureExists(uuid);

    if (assignature.getName() != null) {
      assignatureSaved.setName(assignature.getName());
    }
    if (assignature.getCredits() != null) {
      assignatureSaved.setCredits(assignature.getCredits());
    }

    return assignatureRepository.save(assignatureSaved);
  }

  public void deleteAssignature(UUID uuid) {
    Assignature assignature = assignatureValidations.validateIfAssignatureExists(uuid);

    assignatureRepository.delete(assignature);
  }

  public List<Assignature> getAssignatures() {
    List<Assignature> assignatures = new ArrayList<>();

    assignatureRepository.findAll().forEach(assignature -> assignatures.add(assignature));

    return assignatures;
  }

  public Assignature getAssignature(UUID uuid) {
    return assignatureValidations.validateIfAssignatureExists(uuid);
  }

  public Assignature assignProfessor(UUID uuid, String professorUuid) {
    Assignature assignature = assignatureValidations.validateIfAssignatureExists(uuid);
    Professor professor = professorValidations.validateIfProfessorExists(UUID.fromString(professorUuid));

    assignatureValidations.validateAssignatureTakenByProfessor(assignature, professor);

    assignature.setProfessor(professor);

    return assignatureRepository.save(assignature);
  }

  public Assignature addPrerequisite(UUID uuid, ArrayList<String> prerequisites) {
    Assignature assignature = assignatureValidations.validateIfAssignatureExists(uuid);
    List<UUID> prerequisitesUUID = prerequisites.stream().map(UUID::fromString).toList();
    List<Assignature> assignatures = (List) assignatureRepository.findAllById(prerequisitesUUID);

    assignatureValidations.validatePrerequisite(assignature, prerequisitesUUID);

    assignatures.forEach(assignature1 -> {
      Prerequisite prerequisite = new Prerequisite();
      prerequisite.setPrerequisite(assignature1);
      assignature.addPrerequisite(prerequisite);
    });

    return assignatureRepository.save(assignature);
  }
}
