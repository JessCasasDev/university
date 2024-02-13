package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.repositories.ProfessorRepository;
import com.spring.study.university.University.services.validations.ProfessorValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepository;
  private final ProfessorValidations professorValidations;

  public Professor createProfessor(Professor professor) {
    professorValidations.validateProfessorNoExist(professor.getDocumentNumber());

    Professor professorToSave = new Professor();
    Person person = new Person();
    person.setName(professor.getName());
    person.setEmail(professor.getEmail());
    person.setLastName(professor.getLastName());
    person.setDocumentNumber(professor.getDocumentNumber());

    professorToSave.setPerson(person);

    professorValidations.validateProfessorConstraints(professor);
    return professorRepository.save(professorToSave);
  }

  public void deleteProfessor(UUID uuid) {
    Professor professor = professorValidations.validateIfProfessorExists(uuid);
    professorRepository.delete(professor);
  }

  public Professor editProfessor(UUID uuid, Professor professor) {
    Professor professorSaved = professorValidations.validateIfProfessorExists(uuid);

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
    return professorValidations.validateIfProfessorExists(uuid);
  }

  public List<Professor> getProfessors() {
    ArrayList<Professor> professors = new ArrayList<>();
    professorRepository.findAll().forEach(professor -> professors.add(professor));
    return professors;
  }
}
