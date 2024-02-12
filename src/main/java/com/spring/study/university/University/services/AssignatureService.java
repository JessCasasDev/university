package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Prerequisite;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.AssignatureRepository;
import com.spring.study.university.University.repositories.PrerequisiteRepository;
import com.spring.study.university.University.repositories.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AssignatureService {

  private final AssignatureRepository assignatureRepository;
  private final ProfessorRepository professorRepository;
  private final PrerequisiteRepository prerequisiteRepository;

  public Assignature createAssignature(Assignature assignature) {
    return assignatureRepository.save(assignature);
  }

  public Assignature editAssignature(UUID uuid, Assignature assignature) {
    Assignature assignatureSaved = assignatureRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (assignature.getName() != null) {
      assignatureSaved.setName(assignature.getName());
    }
    if (assignature.getCredits() != null) {
      assignatureSaved.setCredits(assignature.getCredits());
    }

    return assignatureRepository.save(assignatureSaved);
  }

  public void deleteAssignature(UUID uuid) {
    Assignature assignature = assignatureRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    assignatureRepository.delete(assignature);
  }

  public List<Assignature> getAssignatures() {
    List<Assignature> assignatures = new ArrayList<>();

    assignatureRepository.findAll().forEach(assignature -> assignatures.add(assignature));

    return assignatures;
  }

  public Assignature getAssignature(UUID uuid) {
    return assignatureRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public Assignature assignProfessor(UUID uuid, String professorUuid) {
    Assignature assignature = assignatureRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    Professor professor = professorRepository.findByUuid(UUID.fromString(professorUuid))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (assignatureRepository.findByProfessor(professor)
        .stream().anyMatch(assignature1 -> assignature1.equals(assignature))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already taken");
    }

    if (assignature.getProfessor() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already taken");
    }

    assignature.setProfessor(professor);

    return assignatureRepository.save(assignature);
  }

  public Assignature addPrerequisite(UUID uuid, ArrayList<String> prerequisites) {
    Assignature assignature = assignatureRepository
        .findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignature not found"));

    List<UUID> prerequisitesUUID = prerequisites.stream().map((id) -> UUID.fromString(id)).toList();

    List<UUID> commonList = assignature
        .getPrerequisites().stream()
        .map(prerequisite -> prerequisite.getPrerequisite().getUuid())
        .filter(prerequisitesUUID::contains).toList();


    if (!commonList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prerequisite already exists");
    }

    List<Assignature> assignatures = (List) assignatureRepository.findAllById(prerequisitesUUID);

    assignatures.forEach(assignature1 -> {
      Prerequisite prerequisite = new Prerequisite();
      prerequisite.setPrerequisite(assignature1);
      assignature.addPrerequisite(prerequisite);
    });

    return assignatureRepository.save(assignature);
  }
}
