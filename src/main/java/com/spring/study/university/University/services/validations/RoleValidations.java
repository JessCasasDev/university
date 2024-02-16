package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleValidations {

  public void validateRolIsValid(Role role) {
    if (!role.getRole().name().equals(RoleEnum.Student.name())
        && !role.getRole().name().equals(RoleEnum.Professor.name())
        && !role.getRole().name().equals(RoleEnum.Admin.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Role");
    }
  }

  public void validateIfRoleNoExists(Optional<Role> role) {
    role.ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role already exists");
    });
  }
}
