package com.spring.study.university.University.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.services.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/grades")
public class GradeController {

  private final GradeService gradeService;

  @PostMapping("")
  public Grade createGrade(@RequestBody JsonNode body) {
    Long student = body.get("student").asLong();
    String assignature = body.get("assignature").asText();
    Float grade = body.get("grade").floatValue();

    return gradeService.createGrade(student, assignature, grade);
  }

  @PatchMapping("/{uuid}")
  public Grade updateGrade(@PathVariable UUID uuid, @RequestBody JsonNode body) {
    Float grade = body.get("grade").floatValue();

    return gradeService.updateGrade(uuid, grade);
  }
}
