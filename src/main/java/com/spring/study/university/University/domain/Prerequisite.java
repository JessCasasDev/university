package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prerequisites")
public class Prerequisite {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @ManyToOne
  @JoinColumn(name = "assignature_id")
  @JsonIgnore
  @NotNull(message = "Assignature required")
  private Assignature assignature;

  @ManyToOne
  @JoinColumn(name = "prerequisite")
  @NotNull(message = "Prerequisite required")
  private Assignature prerequisite;
}
