package com.spring.study.university.University.controllers;

import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

  private final PersonService personService;

  @PostMapping("")
  @ResponseStatus(HttpStatus.OK)
  public Person createPerson(@RequestBody Person person) {
    return personService.createPerson(person);
  }
}
