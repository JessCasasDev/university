package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends CrudRepository<Admin, UUID> {
  Optional<Admin> findByDocumentNumber(Long documentNumber);
}
