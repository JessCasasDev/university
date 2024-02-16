package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.services.transactions.GradeTransactions;
import com.spring.study.university.University.services.validations.GradeValidations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GradeService {
  private final GradeTransactions gradeTransactions;
  private final GradeValidations gradeValidations;
  private final StudentService studentService;
  private final AssignatureService assignatureService;

  public Grade createGrade(Long studentId, String assignatureId, Float grade) {
    gradeValidations.validateGradeValue(grade);

    Student student = studentService.getStudent(studentId);
    Assignature assignature = assignatureService.getAssignature(UUID.fromString(assignatureId));

    Optional<Grade> gradeByStudentAndAssignature = gradeTransactions.findByStudentAndAssignature(assignature, student);
    gradeValidations.validateIfGradeExistsForStudent(gradeByStudentAndAssignature);

    Grade gradeToSave = gradeTransactions.createGrade(grade, assignature, student);

    gradeValidations.validateGradeFields(gradeToSave);

    return gradeTransactions.saveGrade(gradeToSave);
  }

  public Grade updateGrade(UUID uuid, Float grade) {
    Grade gradeObj = getGradeByUUID(uuid);
    gradeObj.setGrade(grade);
    return gradeTransactions.saveGrade(gradeObj);
  }

  public Grade getGradeByUUID(UUID uuid) {
    Optional<Grade> gradeOptional = gradeTransactions.getGradeByIDD(uuid);

    return gradeValidations.validateIfGradeExists(gradeOptional);
  }

  public Set<Grade> getGradesByStudent(Student student) {
    return gradeTransactions.getGradesByStudent(student);
  }
}
