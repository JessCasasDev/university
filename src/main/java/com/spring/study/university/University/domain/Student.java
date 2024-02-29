package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="students")
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person implements Serializable {
  @Column(name="student_number")
  @NotNull(message = "Student Number must not be null")
  private Long studentNumber;

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "students")
  @JsonIgnore
  private List<ClassList> classLists = new ArrayList<>();

  @OneToMany(cascade =  CascadeType.ALL, mappedBy = "student")
  @JsonIgnore
  private List<Grade> grades = new ArrayList<>();

  @Builder
  public Student(UUID uuid, String name, String lastName,
                 String email, Long documentNumber,
                 Long phoneNumber, Role role, Long studentNumber){
    super(uuid, name, lastName, email, documentNumber, phoneNumber, role);

    this.studentNumber = studentNumber;
  }
}
