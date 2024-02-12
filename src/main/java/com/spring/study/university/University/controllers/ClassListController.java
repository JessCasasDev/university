package com.spring.study.university.University.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.services.ClassListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/class-list")
public class ClassListController {
  private final ClassListService classListService;

  @PostMapping("")
  public ClassList createClass(@RequestBody JsonNode body) {
    String assignature = body.get("assignature").asText();
    Integer maxNumberOfStudents = body.get("maxNumberOfStudents").asInt();

    ArrayList<String> schedules = new ArrayList<>();

    Iterator<JsonNode> schedulesString = body.get("schedules").elements();

    while (schedulesString.hasNext()) {
      schedules.add(schedulesString.next().asText());
    }

    if (assignature == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature is required");
    }


    if (maxNumberOfStudents == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max number of students is required");
    }

    if (schedules.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedules are required");
    }
    return classListService.createClassList(assignature, schedules, maxNumberOfStudents);
  }

  @PatchMapping("/{uuid}/add-student")
  public ClassList addStudent(@PathVariable UUID uuid, @RequestBody JsonNode studentId) {
    String studentUuid = studentId.get("student").asText();

    return classListService.addStudent(uuid, Long.parseLong(studentUuid));
  }

  @GetMapping("/{uuid}/students")
  public List<Student> getStudentsByClass(@PathVariable UUID uuid) {
    return classListService.getStudentsOfClass(uuid);
  }

  @PatchMapping("/{uuid}/remove-student")
  public ClassList removeStudent(@PathVariable UUID uuid, @RequestBody JsonNode studentId) {
    String studentUuid = studentId.get("student").asText();

    return classListService.removeStudent(uuid, Long.parseLong(studentUuid));
  }


  @GetMapping("/{uuid}")
  public ClassList getClassList(@PathVariable UUID uuid) {
    return classListService.getClassList(uuid);
  }


}
