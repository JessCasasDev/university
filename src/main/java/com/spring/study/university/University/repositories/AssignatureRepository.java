package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignatureRepository extends CrudRepository<Assignature, UUID> {

  List<Assignature> findByProfessor(Professor professor);
}
