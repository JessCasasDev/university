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
    Admin newAdmin = new Admin();
    newAdmin.setDocumentNumber(admin.getDocumentNumber());
    newAdmin.setEmail(admin.getEmail());
    newAdmin.setName(admin.getName());
    newAdmin.setLastName(admin.getLastName());
    newAdmin.setRole(admin.getRole());
    newAdmin.setPhoneNumber(admin.getPhoneNumber());

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
