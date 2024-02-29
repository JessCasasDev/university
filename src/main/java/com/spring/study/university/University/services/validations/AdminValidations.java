package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.utils.ConstraintValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminValidations {
  @Autowired
  private final ConstraintValidations constraintValidations;


  public AdminValidations(ConstraintValidations constraintValidations) {
    this.constraintValidations = constraintValidations;
  }

  public void validateAdminNoExists(Optional<Admin> admin) {
    admin.ifPresent(s -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin already exists");
    });
  }

  public Admin validateAdminExists(Optional<Admin> admin) {
    return admin.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
  }

  public void validateAdminRole(Admin admin) {
    if (!admin.getRole().getRole().equals(RoleEnum.Admin)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Role");
    }
  }

  public void validateAdminFields(Admin admin) {
    constraintValidations.validateFields(admin);
  }
}
