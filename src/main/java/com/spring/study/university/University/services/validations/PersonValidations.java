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

  public void validateIfPersonExists(Person person) {
    personRepository.findByDocumentNumber(person.getDocumentNumber()).ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    });
  }

  public void validatePersonFields(Person person) {
    constraintValidations.validateFields(person);
  }
}
