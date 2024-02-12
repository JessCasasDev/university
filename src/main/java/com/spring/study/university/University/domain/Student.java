package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="students")
@NoArgsConstructor
public class Student extends Person implements Serializable {
  @Column(name="student_number")
  @NotNull
  private Long studentNumber;

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "students")
  @JsonIgnore
  private List<ClassList> classLists = new ArrayList<>();

  @OneToMany(cascade =  CascadeType.ALL, mappedBy = "student")
  @JsonIgnore
  private  List<Grade> grades = new ArrayList<>();

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name="person_id")
  private Person person;
}
