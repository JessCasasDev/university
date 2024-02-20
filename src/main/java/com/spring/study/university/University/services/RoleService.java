package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.services.transactions.RoleTransactions;
import com.spring.study.university.University.services.validations.RoleValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class RoleService {

  private final RoleTransactions roleTransactions;
  private final RoleValidations roleValidations;

  @Transactional
  public Role saveRole(Role role) {
    roleValidations.validateRolIsValid(role);
    Optional<Role> roleOptional = roleTransactions.getRole(role);
    roleValidations.validateIfRoleNoExists(roleOptional);
    return roleTransactions.saveRole(role);
  }
}
