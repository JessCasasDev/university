package com.spring.study.university.University.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name="name")
  @NotBlank(message = "Name is required")
  public String name;

  @Column(name="last_name")
  @NotBlank(message = "Last Name is required")
  public String lastName;

  @Column(name="phone_number")
  @Digits(integer = 7, fraction = 0, message = "Phone number length must be 7")
  @NotNull(message = "Phone number is required")
  public Long phoneNumber;

  @Column(name="email")
  @NotNull(message = "Email is required")
  @Email(message = "Wrong email pattern")
  public String email;

  @Column(name = "document_number")
  @NotNull(message = "Document is required")
  public Long documentNumber;

  @ManyToOne
  @JoinColumn(name = "role")
  @NotNull(message = "Role is required")
  public Role role;

  public Person(UUID uuid, String name, String lastName, String email, Long documentNumber, Long phoneNumber, Role role) {
    this.uuid = uuid;
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.documentNumber = documentNumber;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }
}
