package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "class_list")
@NoArgsConstructor
@AllArgsConstructor
public class ClassList implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name = "max_number_of_students", nullable = false)
  @Range(min = 10, max = 50, message = "The students per class must be between 10 and 50")
  private Integer maxNumberOfStudents;
  @ManyToMany
  @JsonIgnore
  private final Set<Student> students = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "assignature")
  @NotNull
  private Assignature assignature;

  @ManyToMany
  @JoinTable(
      name = "schedule_class_list",
      joinColumns = @JoinColumn(name = "class_list"), inverseJoinColumns = @JoinColumn(name = "schedule"))
  private final Set<Schedule> schedules = new HashSet();

}
