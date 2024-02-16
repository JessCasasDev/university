package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.utils.ConstraintValidations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ClassListValidations {
  private final ConstraintValidations constraintValidations;

  public ClassList validateIfClassListExists(Optional<ClassList> classList) {
    return classList.orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Class List not found"));
  }

  public void validateIfClassIsFull(ClassList classList) {
    if (classList.getStudents().size() == classList.getMaxNumberOfStudents()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No more students accepted");
    }
  }

  public void validateIfStudentInClass(ClassList classList, Student student){
    if (classList.getStudents().contains(student)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already in class");
    }
  }

  public void validateClassListFields(ClassList classList) {
    constraintValidations.validateFields(classList);
  }
}
