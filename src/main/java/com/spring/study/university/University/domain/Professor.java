package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "professors")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Professor extends Person implements Serializable {
  @OneToMany(mappedBy = "professor")
  @JsonIgnore
  private Set<Assignature> assignatures = new HashSet<>();

  @Builder
  public Professor(UUID uuid,
                   String name,
                   String lastName,
                   String email,
                   Long documentNumber,
                   Long phoneNumber,
                   Role role) {
    super(uuid, name, lastName, email, documentNumber, phoneNumber, role);
  }
}
