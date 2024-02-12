package com.spring.study.university.University.controllers;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {

  private final RoleService roleService;

  @PostMapping("")
  public Role saveRole(@RequestBody Role role) {
    return roleService.saveRole(role);
  }
}
