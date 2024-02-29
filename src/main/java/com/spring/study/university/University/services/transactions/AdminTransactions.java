package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminTransactions {
  @Autowired
  private final AdminRepository adminRepository;

  public AdminTransactions(AdminRepository adminRepository) {
    this.adminRepository = adminRepository;
  }

  public Admin createAdmin(Admin admin) {
    Admin newAdmin = Admin.builder()
        .documentNumber(admin.getDocumentNumber())
        .email(admin.getEmail())
        .name(admin.getName())
        .lastName(admin.getLastName())
        .role(admin.getRole())
        .phoneNumber(admin.getPhoneNumber()).build();

    return newAdmin;
  }

  public Admin saveAdmin(Admin admin) {
    return adminRepository.save(admin);
  }

  public Optional<Admin> getAdmin(UUID uuid) {
    return adminRepository.findById(uuid);
  }

  public Admin updateAdmin(Admin adminSaved, Admin admin) {
    if (admin.getName() != null) {
      adminSaved.setName(admin.getName());
    }
    if (admin.getLastName() != null) {
      adminSaved.setLastName(admin.getLastName());
    }
    if (admin.getPhoneNumber() != null) {
      adminSaved.setPhoneNumber(admin.getPhoneNumber());
    }

    return adminSaved;
  }

  public void deleteAdmin(Admin admin) {
    adminRepository.delete(admin);
  }

  public Optional<Admin> getAdminByDocument(Long documentNumber) {
    return adminRepository.findByDocumentNumber(documentNumber);
  }
}
