package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.services.transactions.ProfessorTransactions;
import com.spring.study.university.University.services.validations.ProfessorValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfessorService {

  private final ProfessorTransactions professorTransactions;
  private final ProfessorValidations professorValidations;

  public Professor createProfessor(Professor professor) {
    Optional<Professor> professorOptional = professorTransactions.getProfessorByDocument(professor.getDocumentNumber());
    professorValidations.validateProfessorNoExist(professorOptional);

    Professor professorToSave = professorTransactions.createProfessor(professor);
    professorValidations.validateProfessorRole(professorToSave);
    professorValidations.validateProfessorConstraints(professorToSave);

    return professorTransactions.saveProfessor(professor);
  }

  public void deleteProfessor(UUID uuid) {
    Professor professor = getProfessor(uuid);
    professorTransactions.deleteProfessor(professor);
  }

  public Professor editProfessor(UUID uuid, Professor professor) {
    Professor professorSaved = getProfessor(uuid);
    Professor professorEdited = professorTransactions.editProfessor(professorSaved, professor);

    return professorTransactions.saveProfessor(professorEdited);
  }

  public Professor getProfessor(UUID uuid) {
    Optional<Professor> professorOptional = professorTransactions.getProfessorById(uuid);

    return professorValidations.validateIfProfessorExists(professorOptional);
  }

  public List<Professor> getProfessors() {
    return professorTransactions.getProfessors();
  }
}
