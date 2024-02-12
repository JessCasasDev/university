package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {

  Optional<Person> findByDocumentNumber(Long documentNumber);
}
