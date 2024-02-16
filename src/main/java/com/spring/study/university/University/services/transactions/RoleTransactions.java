package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RoleTransactions {
  private final RoleRepository roleRepository;

  public Role saveRole(Role role){
    return roleRepository.save(role);
  }

  public Optional<Role> getRole(Role role) {
    return roleRepository.findByRole(role.getRole());
  }
}
