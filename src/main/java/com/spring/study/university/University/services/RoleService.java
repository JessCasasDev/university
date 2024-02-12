package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@AllArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public Role saveRole(Role role) {
    if (!role.getRole().name().equals(RoleEnum.Student.name())
        && !role.getRole().name().equals(RoleEnum.Professor.name())
        && !role.getRole().name().equals(RoleEnum.Admin.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if(roleRepository.findByRole(role.getRole()).isPresent()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role already exists");
    }
    return roleRepository.save(role);
  }
}
