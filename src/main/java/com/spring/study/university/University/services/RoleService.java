package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.repositories.RoleRepository;
import com.spring.study.university.University.services.validations.RoleValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final RoleValidations roleValidations;

  public Role saveRole(Role role) {
    roleValidations.validateRolIsValid(role);
    roleValidations.validateIfRoleNoExists(role);
    return roleRepository.save(role);
  }
}
