package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {

  Optional<Role> findByRole(RoleEnum role);
}
