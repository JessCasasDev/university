package com.spring.study.university.University.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.study.university.University.enums.Day;
import com.spring.study.university.University.utils.DayEnumValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Column(name = "day")
  @Enumerated(EnumType.STRING)
  @NotNull(message = "Day is required")
  @DayEnumValidator(anyOf = {Day.Monday, Day.Thursday, Day.Wednesday, Day.Thursday, Day.Friday})
  private Day day;

  @Column(name = "initial_time")
  @JsonFormat(pattern = "HH:mm")
  @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")
  private LocalTime initialTime;

  @Column(name = "end_time")
  @JsonFormat(pattern = "HH:mm")
  @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")
  private LocalTime endTime;

  @ManyToMany(mappedBy = "schedules")
  @JsonIgnore
  private List<ClassList> classesList;
}
