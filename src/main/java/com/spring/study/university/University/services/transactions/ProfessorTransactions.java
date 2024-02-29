package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProfessorTransactions {
  private final ProfessorRepository professorRepository;

  public Professor createProfessor(Professor professor) {
    Professor professorToSave = Professor.builder()
        .name(professor.getName())
        .email(professor.getEmail())
        .lastName(professor.getLastName())
        .documentNumber(professor.getDocumentNumber())
        .phoneNumber(professor.getPhoneNumber())
        .role(professor.getRole()).build();

    return professorToSave;

  }

  public Professor saveProfessor(Professor professor) {
    return professorRepository.save(professor);
  }

  public void deleteProfessor(Professor professor) {
    professorRepository.delete(professor);
  }

  public Professor editProfessor(Professor professorSaved, Professor professorNew) {
    if (professorNew.getName() != null) {
      professorSaved.setName(professorNew.getName());
    }
    if (professorNew.getLastName() != null) {
      professorSaved.setLastName(professorNew.getLastName());
    }
    if (professorNew.getPhoneNumber() != null) {
      professorSaved.setPhoneNumber(professorNew.getPhoneNumber());
    }

    return professorSaved;
  }

  public Optional<Professor> getProfessorById(UUID uuid) {
    return professorRepository.findByUuid(uuid);
  }


  public Optional<Professor> getProfessorByDocument(Long documentNumber) {
    return professorRepository.findByDocumentNumber(documentNumber);
  }

  public List<Professor> getProfessors() {
    ArrayList<Professor> professors = new ArrayList<>();
    professorRepository.findAll().forEach(professors::add);
    return professors;
  }
}
