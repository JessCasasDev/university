package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.repositories.PersonRepository;
import com.spring.study.university.University.repositories.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepository;
  private final PersonRepository personRepository;

  public Professor createProfessor(String personId) {
    UUID uuid = UUID.fromString(personId);
    Person person = personRepository
        .findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    Optional<Professor> professorSaved = professorRepository.findByUuid(uuid);

    if (professorSaved.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor already exists");
    }

    if (!person.getRole().getRole().name().equals(RoleEnum.Professor.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Professor professorToSave = new Professor();
    professorToSave.setUuid(uuid);
    professorToSave.setPerson(person);
    return professorRepository.save(professorToSave);
  }

  public void deleteProfessor(UUID uuid) {
    Professor professor = professorRepository.findByUuid(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    professorRepository.delete(professor);
  }

  public Professor editProfessor(UUID uuid, Professor professor) {
    Professor professorSaved = professorRepository.findByUuid(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (professor.getName() != null) {
      professorSaved.setName(professor.getName());
    }
    if (professor.getLastName() != null) {
      professorSaved.setLastName(professor.getLastName());
    }
    if (professor.getPhoneNumber() != null) {
      professorSaved.setPhoneNumber(professor.getPhoneNumber());
    }

    return
        professorRepository.save(professorSaved);
  }

  public Professor getProfessor(UUID uuid) {
    return professorRepository.findByUuid(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public List<Professor> getProfessors() {
    ArrayList<Professor> professors = new ArrayList<>();
    professorRepository.findAll().forEach(professor -> professors.add(professor));
    return professors;
  }
}
