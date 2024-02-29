package com.spring.study.university.University.domain;


import com.spring.study.university.University.domain.DTO.GradeDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="grades")
public class Grade implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name="grade")
  @Range(min=0, max = 5, message = "Grade value must be between 0 and 5")
  private Float grade;

  @ManyToOne
  @JoinColumn(name = "student")
  @NotNull
  private Student student;

  @ManyToOne
  @JoinColumn(name = "assignature")
  @NotNull
  private Assignature assignature;

  public Grade(GradeDTO grade) {
    this.uuid = grade.getUUID();
    this.grade = grade.getGrade();
  }
}
