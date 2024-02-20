package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.services.transactions.StudentTransactions;
import com.spring.study.university.University.services.validations.StudentValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
  private final StudentTransactions studentTransactions;
  private final StudentValidations studentValidations;

  @Transactional
  public Student createStudent(Student student) {
    studentValidations.validateStudentNoExists(student.getStudentNumber());
    studentValidations.validateStudentRole(student);

    Student studentToSave = studentTransactions.createStudent(student);

    studentValidations.validateStudentFields(studentToSave);

    return studentTransactions.saveStudent(studentToSave);
  }

  @Transactional
  public Student updateStudent(Long studentNumber, Student student) {
    Student studentSaved = getStudent(studentNumber);

    Student studentUpdated = studentTransactions.updateStudent(studentSaved, student);
    studentValidations.validateStudentFields(studentUpdated)    ;
    return studentTransactions.saveStudent(studentUpdated);
  }

  @Transactional
  public void deleteStudent(Long studentNumber) {
    Student student = getStudent(studentNumber);

    studentTransactions.deleteStudent(student);
  }

  public Student getStudent(Long studentNumber){
    Optional<Student> studentOptional = studentTransactions.getStudentByStudentNumber(studentNumber);
    return studentValidations.validateIfStudentExists(studentOptional);
  }

  public List<Grade> getStudentGrades(Long studentNumber) {
    Student student = getStudent(studentNumber);

    return  student.getGrades();
  }
}
