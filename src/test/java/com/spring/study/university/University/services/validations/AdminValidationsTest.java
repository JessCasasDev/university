package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.utils.ConstraintValidations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminValidationsTest {
  @Mock
  ConstraintValidations constraintValidations;

  @InjectMocks
  AdminValidations adminValidations;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    adminValidations = new AdminValidations(constraintValidations);
  }

  @Test
  void validateAdminNoExistsError() {
    Admin admin = createAdmin();

    Assertions.assertThrows(ResponseStatusException.class,
        () -> adminValidations.validateAdminNoExists(Optional.of(admin))
    );
  }

  @Test
  void validateAdminNoExists() {
    assertDoesNotThrow(
        () -> adminValidations.validateAdminNoExists(Optional.empty())
    );
  }

  @Test
  void validateAdminExists() {
    Admin admin = createAdmin();
    assertDoesNotThrow(
        () -> adminValidations.validateAdminExists(Optional.of(admin))
    );
  }

  @Test
  void validateAdminExistsError() {
    Assertions.assertThrows(ResponseStatusException.class,
        () -> adminValidations.validateAdminExists(Optional.empty())
    );
  }

  @Test
  void validateAdminRoleError() {
    Role role = new Role(RoleEnum.Professor);
    Admin admin = createAdmin();
    admin.setRole(role);

    Assertions.assertThrows(ResponseStatusException.class,
        () -> adminValidations.validateAdminRole(admin)
    );
  }

  @Test
  void validateAdminRole() {
    Admin admin = createAdmin();
    assertDoesNotThrow(
        () -> adminValidations.validateAdminRole(admin)
    );
  }

  @Test
  void validateAdminFieldsError() {
    Admin admin = new Admin();

    doThrow(ResponseStatusException.class)
        .when(constraintValidations)
        .validateFields(admin);

    Assertions.assertThrows(ResponseStatusException.class,
        () -> adminValidations.validateAdminFields(admin)
    );
  }

  @Test
  void validateAdminFields() {
    Admin admin = createAdmin();
    adminValidations.validateAdminFields(admin);
    verify(constraintValidations).validateFields(admin);
  }


  private Admin createAdmin() {
    Admin admin = new Admin();
    admin.setName("Admin");
    admin.setLastName("Lastname");
    admin.setPhoneNumber(1234567L);
    admin.setEmail("admin@email.com");
    admin.setRole(new Role(RoleEnum.Admin));

    return admin;
  }
}