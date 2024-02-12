package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.GradeRepository;
import com.spring.study.university.University.services.validations.AssignatureValidations;
import com.spring.study.university.University.services.validations.GradeValidations;
import com.spring.study.university.University.services.validations.StudentValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class GradeService {
  private final GradeRepository gradeRepository;
  private final GradeValidations gradeValidations;
  private final StudentValidations studentValidations;
  private final AssignatureValidations assignatureValidations;

  public Grade createGrade(Long studentId, String assignatureId, Float grade) {
    gradeValidations.validateGradeValue(grade);

    Student student = studentValidations.validateIfStudentExists(studentId);
    Assignature assignature = assignatureValidations
        .validateIfAssignatureExists(UUID.fromString(assignatureId));

    gradeValidations.validateIfGradeExistsForStudent(student, assignature);

    Grade gradeObj = new Grade();
    gradeObj.setGrade(grade);
    gradeObj.setAssignature(assignature);
    gradeObj.setStudent(student);

    gradeValidations.validateGradeFields(gradeObj);

    return gradeRepository.save(gradeObj);
  }

  public Grade updateGrade(UUID uuid, Float grade) {
    Grade gradeObj = gradeValidations.validateIfGradeExists(uuid);
    gradeObj.setGrade(grade);
    return gradeRepository.save(gradeObj);
  }
}
