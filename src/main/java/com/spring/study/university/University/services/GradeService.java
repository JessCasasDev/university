package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.AssignatureRepository;
import com.spring.study.university.University.repositories.GradeRepository;
import com.spring.study.university.University.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@Service
public class GradeService {
  private final GradeRepository gradeRepository;
  private final StudentRepository studentRepository;
  private final AssignatureRepository assignatureRepository;

  public Grade createGrade(Long studentId, String assignatureId, Float grade) {
    if (grade > 5 || grade < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grade must be between 0 and 5");
    }
    Student student = studentRepository
        .findByStudentNumber(studentId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

    Assignature assignature = assignatureRepository
        .findById(UUID.fromString(assignatureId))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignature not found"));

    if (gradeRepository.findByStudentAndAssignature(student, assignature).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature already graded to Student");
    }

    Grade gradeObj = new Grade();
    gradeObj.setGrade(grade);
    gradeObj.setAssignature(assignature);
    gradeObj.setStudent(student);

    return gradeRepository.save(gradeObj);
  }

  public Grade updateGrade(UUID uuid, Float grade) {
    Grade gradeObj = gradeRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    gradeObj.setGrade(grade);

    return gradeRepository.save(gradeObj);
  }
}
