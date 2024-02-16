package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.AssignatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AssignatureTransactions {

  private final AssignatureRepository assignatureRepository;

  public Assignature saveAssignature(Assignature assignature) {
    return assignatureRepository.save(assignature);
  }

  public Assignature updateAssignature(Assignature assignatureSaved, Assignature assignatureNew) {
    if (assignatureNew.getName() != null) {
      assignatureSaved.setName(assignatureNew.getName());
    }
    if (assignatureNew.getCredits() != null) {
      assignatureSaved.setCredits(assignatureNew.getCredits());
    }

    return assignatureRepository.save(assignatureSaved);
  }

  public void deleteAssignature(Assignature assignature) {
    assignatureRepository.delete(assignature);
  }

  public List<Assignature> getAllAssignatures() {
    List<Assignature> assignatures = new ArrayList<>();

    assignatureRepository
        .findAll()
        .forEach(assignatures::add);

    return assignatures;
  }

  public List<Assignature> findAssignaturesByIds(List<UUID> assignatures) {
    return (List<Assignature>) assignatureRepository.findAllById(assignatures);
  }

  public Optional<Assignature> findById(UUID uuid) {
    return assignatureRepository
        .findById(uuid);
  }

  public Optional<Assignature> findByNameAndCredits(String name, Integer credits) {
    return assignatureRepository
        .findByNameAndCredits(name, credits);
  }

  public List<Assignature> findByProfessor(Professor professor) {
    return assignatureRepository
        .findByProfessor(professor);
  }
}
