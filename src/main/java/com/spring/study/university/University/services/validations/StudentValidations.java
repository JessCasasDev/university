package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.repositories.StudentRepository;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentValidations {
  private final StudentRepository studentRepository;
  private final ConstraintValidations constraintValidations;


  public Student validateIfStudentExists(Optional<Student> student) {
    return student.
        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
  }

  public void validateStudentNoExists(Long id) {
    studentRepository.findByStudentNumber(id).
        ifPresent(s -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already exists"));
  }

  public void validateStudentRole(Student student){
    if (!student.getRole().getRole().name().equals(RoleEnum.Student.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Role");
    }
  }

  public void validateStudentFields(Student studentToSave) {
    constraintValidations.validateFields(studentToSave);
  }
}
