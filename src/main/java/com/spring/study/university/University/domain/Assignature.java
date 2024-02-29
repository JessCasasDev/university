package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "assignature")
public class Assignature implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name = "name")
  @NotBlank(message = "Name is required")
  private String name;

  @Column(name = "credits")
  @Range(min = 1, max = 5, message = "Credits allowed between 1 and 5")
  private Integer credits;

  @ManyToMany(mappedBy = "assignature")
  @JsonIgnore
  private final List<ClassList> classLists = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Professor professor;

  @OneToMany(mappedBy = "assignature", cascade = CascadeType.ALL)
  private final Set<Prerequisite> prerequisites = new HashSet<>();

  public void addPrerequisite(Prerequisite prerequisite) {
    this.prerequisites.add(prerequisite);
    prerequisite.setAssignature(this);
  }
}
