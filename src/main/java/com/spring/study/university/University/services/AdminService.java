package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.services.transactions.AdminTransactions;
import com.spring.study.university.University.services.validations.AdminValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AdminService {
  private final AdminTransactions adminTransactions;
  private final AdminValidations adminValidations;

  @Transactional
  public Admin addAdmin(Admin admin){
    Optional<Admin> adminOptional = adminTransactions.getAdminByDocument(admin.getDocumentNumber());
    adminValidations.validateAdminNoExists(adminOptional);

    adminValidations.validateAdminRole(admin);
    adminValidations.validateAdminFields(admin);

    Admin newAdmin = adminTransactions.createAdmin(admin);

    return adminTransactions.saveAdmin(newAdmin);
  }


  public Admin getAdmin(UUID uuid){
    Optional<Admin> admin = adminTransactions.getAdmin(uuid);

    return adminValidations.validateAdminExists(admin);
  }

  @Transactional
  public Admin updateAdmin(UUID uuid, Admin admin) {
    Admin adminSaved = getAdmin(uuid);

    Admin adminUpdated = adminTransactions.updateAdmin(adminSaved, admin);

    adminValidations.validateAdminFields(adminUpdated);

    return adminTransactions.saveAdmin(adminUpdated);
  }

  @Transactional
  public void deleteAdmin(UUID uuid) {
    Admin admin = getAdmin(uuid);

    adminTransactions.deleteAdmin(admin);
  }
}
