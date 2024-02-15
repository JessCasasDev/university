package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.repositories.PersonRepository;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class PersonValidations {

  private final PersonRepository personRepository;
  private final ConstraintValidations constraintValidations;

  public void validatePersonNoExists(Person person) {
    personRepository.findByDocumentNumber(person.getDocumentNumber()).ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person already exists");
    });
  }

  public Person validatePersonExists(Long documentNumber) {
    return personRepository
        .findByDocumentNumber(documentNumber)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
  }


  public void validatePersonFields(Person person) {
    constraintValidations.validateFields(person);
  }
}
