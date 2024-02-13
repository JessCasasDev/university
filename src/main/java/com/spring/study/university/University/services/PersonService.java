package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.repositories.PersonRepository;
import com.spring.study.university.University.services.validations.PersonValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;
  private final PersonValidations personValidations;

  public Person createPerson(Person person) {
    personValidations.validatePersonNoExists(person);
    personValidations.validatePersonFields(person);

    return personRepository.save(person);
  }
}
