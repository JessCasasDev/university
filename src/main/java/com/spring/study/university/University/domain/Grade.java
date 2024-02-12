package com.spring.study.university.University.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name="grades")
public class Grade implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name="grade")
  @Range(min=0, max = 5, message = "The grade value must be between 0 and 5")
  private Float grade;

  @ManyToOne
  @JoinColumn(name = "student")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "assignature")
  private Assignature assignature;
}
