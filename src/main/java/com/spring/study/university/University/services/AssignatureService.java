package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Prerequisite;
import com.spring.study.university.University.domain.Professor;

import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.services.transactions.AssignatureTransactions;
import com.spring.study.university.University.services.validations.AssignatureValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AssignatureService {

  private final AssignatureTransactions assignatureTransactions;
  private final AssignatureValidations assignatureValidations;
  private final ProfessorService professorService;

  @Transactional
  public Assignature createAssignature(Assignature assignature) {
    Optional<Assignature> assignatureOptional = assignatureTransactions
        .findByNameAndCredits(assignature.getName(), assignature.getCredits());
    assignatureValidations.validateAssignatureInformationCreation(assignatureOptional);

    return assignatureTransactions.saveAssignature(assignature);
  }

  @Transactional
  public Assignature editAssignature(UUID uuid, Assignature assignatureNew) {
    Assignature assignature = getAssignature(uuid);

    return assignatureTransactions.updateAssignature(assignature, assignatureNew);
  }

  @Transactional
  public void deleteAssignature(UUID uuid) {
    Assignature assignature = getAssignature(uuid);

    assignatureTransactions.deleteAssignature(assignature);
  }

  public List<Assignature> getAssignatures() {
    return assignatureTransactions.getAllAssignatures();
  }

  public Assignature getAssignature(UUID uuid) {
    Optional<Assignature> assignatureOptional = assignatureTransactions.findById(uuid);
    return assignatureValidations.validateIfAssignatureExists(assignatureOptional);
  }

  @Transactional
  public Assignature assignProfessor(UUID uuid, String professorUuid) {
    Assignature assignature = getAssignature(uuid);
    UUID professorId = UUID.fromString(professorUuid);
    Professor professor = professorService.getProfessor(professorId);
    List<Assignature> professorAssignatures = assignatureTransactions.findByProfessor(professor);
    assignatureValidations.validateAssignatureTakenByProfessor(assignature, professorAssignatures);

    assignature.setProfessor(professor);

    return assignatureTransactions.saveAssignature(assignature);
  }

  @Transactional
  public Assignature addPrerequisite(UUID uuid, ArrayList<String> prerequisites) {
    Assignature assignature = getAssignature(uuid);

    List<UUID> prerequisitesUUID = prerequisites
        .stream()
        .map(UUID::fromString)
        .toList();

    List<Assignature> assignatures = assignatureTransactions.findAssignaturesByIds(prerequisitesUUID);

    assignatureValidations.validatePrerequisite(assignature, prerequisitesUUID);

    assignatures.forEach(assignature1 -> {
      Prerequisite prerequisite = new Prerequisite();
      prerequisite.setPrerequisite(assignature1);
      assignature.addPrerequisite(prerequisite);
    });

    return assignatureTransactions.saveAssignature(assignature);
  }

  public void getPrerequisitesValidations(Assignature assignature, Student student) {
    assignatureValidations.validateAssignaturePrerequisiteValid(assignature, student);
  }
}
