package com.spring.study.university.University.domain;

import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.utils.RoleEnumValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  @RoleEnumValidator(anyOf = {RoleEnum.Admin, RoleEnum.Professor, RoleEnum.Student})
  private RoleEnum role;

  public Role(RoleEnum role){
    this.role = role;
  }
}
