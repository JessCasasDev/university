package com.spring.study.university.University.services;


import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.services.transactions.AdminTransactions;
import com.spring.study.university.University.services.validations.AdminValidations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

  @Mock
  AdminValidations adminValidations;
  @Mock
  AdminTransactions adminTransactions;

  @InjectMocks
  AdminService adminService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    adminService = new AdminService(adminTransactions, adminValidations);
  }

  @Test
  void addAdmin() {
    Admin admin = createAdmin();

    when(adminTransactions.getAdminByDocument(admin.documentNumber)).thenReturn(null);
    when(adminTransactions.createAdmin(admin)).thenReturn(admin);
    when(adminTransactions.saveAdmin(admin)).thenReturn(admin);

    Admin adminSaved = adminService.addAdmin(admin);

    assertNotNull(adminSaved);
  }

  @Test
  void getAdmin() {
    Admin admin = createAdmin();
    Optional<Admin> optionalAdmin = Optional.of(admin);

    when(adminTransactions.getAdmin(admin.getUuid())).thenReturn(optionalAdmin);
    when(adminValidations.validateAdminExists(optionalAdmin)).thenReturn(admin);

    Admin adminSaved = adminService.getAdmin(admin.getUuid());

    assertNotNull(adminSaved);
  }

  @Test
  void updateAdmin() {
    Admin admin = createAdmin();
    admin.setUuid(UUID.randomUUID());

    Admin newAdmin = Admin.builder()
        .uuid(admin.getUuid())
        .name("Another name")
        .email("another_email@email.com")
        .lastName("Another LastName")
        .build();

    Admin result = new Admin();
    BeanUtils.copyProperties(admin, result);
    result.setLastName(newAdmin.getLastName());
    result.setName(newAdmin.getName());

    when(adminService.getAdmin(admin.getUuid())).thenReturn(admin);
    when(adminTransactions.updateAdmin(admin, newAdmin)).thenReturn(result);
    when(adminTransactions.saveAdmin(result)).thenReturn(result);

    Admin adminTest = adminService.updateAdmin(admin.getUuid(), newAdmin);

    assertNotNull(adminTest);
    assertEquals(adminTest.getUuid(), admin.getUuid());
    assertEquals(adminTest.getEmail(), admin.getEmail());
    assertEquals(adminTest.getName(), newAdmin.getName());
    assertEquals(adminTest.getLastName(), newAdmin.getLastName());
  }

  @Test
  void deleteAdmin() {
    Admin admin = createAdmin();

    when(adminService.getAdmin(admin.getUuid())).thenReturn(admin);

    adminService.deleteAdmin(admin.getUuid());

    verify(adminTransactions).deleteAdmin(admin);
  }


  private Admin createAdmin() {
    Role role = Role.builder()
        .role(RoleEnum.Admin)
        .build();

    Admin admin = Admin.builder()
        .name("Admin")
        .lastName("Lastname")
        .phoneNumber(1234567L)
        .email("admin@email.com")
        .role(role)
        .build();

    return admin;
  }

}