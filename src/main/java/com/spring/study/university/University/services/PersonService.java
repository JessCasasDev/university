package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  public Person createPerson(Person person) {
    if (personRepository
        .findByDocumentNumber(person.getDocumentNumber())
        .isPresent()) {
      new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    return personRepository.save(person);
  }
}
