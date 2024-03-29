package com.spring.study.university.University.controllers;

import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/student")
@RestController
public class StudentController {
  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/{studentNumber}")
  public Student getStudentByStudentNumber(@PathVariable Long studentNumber) {
    return studentService.getStudent(studentNumber);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Student createStudent(@RequestBody Student student) {
    return studentService.createStudent(student);
  }

  @PatchMapping("/{studentNumber}")
  public Student updateStudent(@PathVariable Long studentNumber, @RequestBody Student student) {
    return studentService.updateStudent(studentNumber, student);
  }

  @DeleteMapping("{studentNumber}")
  public void deleteStudent(@PathVariable Long studentNumber) {
    studentService.deleteStudent(studentNumber);
  }

  @GetMapping("/{studentNumber}/grades")
  public List<Grade> getStudentGrades(@PathVariable Long studentNumber) {
    return studentService.getStudentGrades(studentNumber);
  }
}
