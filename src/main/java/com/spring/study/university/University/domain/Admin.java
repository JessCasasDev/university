package com.spring.study.university.University.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name="admins")
@Entity
@NoArgsConstructor
public class Admin extends Person {

  @Builder
  public Admin(UUID uuid,
               String name,
               String lastName,
               String email,
               Long documentNumber,
               Long phoneNumber,
               Role role) {
    super(uuid, name, lastName, email, documentNumber, phoneNumber, role);
  }
}
