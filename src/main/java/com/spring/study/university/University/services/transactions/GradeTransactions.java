package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.GradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GradeTransactions {
  private final GradeRepository gradeRepository;

  public Grade createGrade(Float grade, Assignature assignature, Student student) {

    Grade gradeObj = new Grade();
    gradeObj.setGrade(grade);
    gradeObj.setAssignature(assignature);
    gradeObj.setStudent(student);

    return gradeObj;
  }

  public Grade saveGrade(Grade grade) {
    return gradeRepository.save(grade);
  }

  public Optional<Grade> getGradeByIDD(UUID uuid) {
    return gradeRepository.findById(uuid);
  }

  public Optional<Grade> findByStudentAndAssignature(Assignature assignature, Student student) {
    return gradeRepository.findByStudentAndAssignature(student, assignature);
  }
}
