package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.ClassListRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ClassListValidations {
  private final ClassListRepository classListRepository;

  public ClassList validateIfClassListExists(UUID uuid) {
    return classListRepository.findById(uuid).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.BAD_REQUEST));
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

}
