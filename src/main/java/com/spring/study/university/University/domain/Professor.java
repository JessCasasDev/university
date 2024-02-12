package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="professors")
@Getter
@Setter
public class Professor extends Person implements Serializable {

  @OneToMany(mappedBy = "professor")
  @JsonIgnore
  private Set<Assignature> assignatures = new HashSet<>();


  @OneToOne
  @JoinColumn(name="person_id")
  @NotNull
  private Person person;
}
