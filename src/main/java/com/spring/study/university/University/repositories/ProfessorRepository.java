package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {

  Optional<Professor> findByDocumentNumber(Long documentNumber);

  Optional<Professor> findByUuid(UUID uuid);
}
