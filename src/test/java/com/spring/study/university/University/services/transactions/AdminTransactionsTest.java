package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminTransactionsTest {

  @Mock
  AdminRepository adminRepository;

  @InjectMocks
  AdminTransactions adminTransactions;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    adminTransactions = new AdminTransactions(adminRepository);
  }

  @Test
  void createAdmin() {
    Admin admin = getAdminInfo();

    assertNotNull(adminTransactions.createAdmin(admin));
  }

  @Test
  void saveAdmin() {
    Admin admin = getAdminInfo();

    when(adminRepository.save(admin))
        .thenReturn(admin);

    Admin newAdmin = adminTransactions.saveAdmin(admin);
    assertNotNull(newAdmin);
  }

  @Test
  void getAdmin() {
    Admin admin = getAdminInfo();

    when(adminRepository.findById(admin.getUuid()))
        .thenReturn(Optional.of(admin));

    Optional<Admin> adminOptional = adminTransactions.getAdmin(admin.getUuid());
    assertNotNull(adminOptional);
    assertTrue(adminOptional.isPresent());
  }

  @Test
  void getAdminEmpty() {
    UUID uuid = UUID.randomUUID();
    when(adminRepository.findById(uuid))
        .thenReturn(Optional.empty());

    Optional<Admin> admin = adminTransactions.getAdmin(uuid);
    assertNotNull(admin);
    assertTrue(admin.isEmpty());
  }

  @Test
  void updateAdmin() {
    Admin admin = getAdminInfo();
    Admin newInfo = new Admin();
    newInfo.setName("Other name");
    newInfo.setLastName("Other name");
    newInfo.setPhoneNumber(456L);
    newInfo.setEmail("other_email@email.com");
    newInfo.setUuid(UUID.randomUUID());
    newInfo.setRole(new Role(RoleEnum.Professor));
    newInfo.setDocumentNumber(888L);

    Admin savedAdmin = adminTransactions.updateAdmin(admin, newInfo);

    assertEquals(savedAdmin.getLastName(), newInfo.getLastName());
    assertEquals(savedAdmin.getName(), newInfo.getName());
    assertEquals(savedAdmin.getPhoneNumber(), newInfo.getPhoneNumber());

    assertNotEquals(savedAdmin.getEmail(), newInfo.getEmail());
    assertNotEquals(savedAdmin.getDocumentNumber(), newInfo.getDocumentNumber());
    assertNotEquals(savedAdmin.getRole(), newInfo.getRole());
    assertNotEquals(savedAdmin.getUuid(), newInfo.getUuid());

    assertEquals(savedAdmin.getEmail(), admin.getEmail());
    assertEquals(savedAdmin.getDocumentNumber(), admin.getDocumentNumber());
    assertEquals(savedAdmin.getRole(), admin.getRole());
    assertEquals(savedAdmin.getUuid(), admin.getUuid());
  }

  @Test
  void deleteAdmin() {
    Admin admin = getAdminInfo();

    adminTransactions.deleteAdmin(admin);

    verify(adminRepository).delete(admin);
  }

  @Test
  void getAdminByDocument() {
    Admin admin = getAdminInfo();

    when(adminRepository.findByDocumentNumber(admin.getDocumentNumber()))
        .thenReturn(Optional.of(admin));

    Optional<Admin> adminOptional = adminTransactions.getAdminByDocument(admin.getDocumentNumber());

    assertNotNull(adminOptional);
    assertTrue(adminOptional.isPresent());
  }

  @Test
  void getAdminByDocumentEmpty() {
    Long id = 1L;
    when(adminRepository.findByDocumentNumber(id))
        .thenReturn(Optional.empty());

    Optional<Admin> admin = adminTransactions.getAdminByDocument(id);

    assertNotNull(admin);
    assertTrue(admin.isEmpty());
  }

  private Admin getAdminInfo() {
    Admin admin = new Admin();
    admin.setName("Admin");
    admin.setLastName("Lastname");
    admin.setPhoneNumber(1234567L);
    admin.setEmail("admin@email.com");
    admin.setRole(new Role(RoleEnum.Admin));

    return admin;
  }
}