package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class StudentValidations {
  private final StudentRepository studentRepository;


  public Student validateIfStudentExists(Long id) {
    return studentRepository.findByStudentNumber(id).
        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

}
