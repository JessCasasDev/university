package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.GradeRepository;
import com.spring.study.university.University.repositories.PersonRepository;
import com.spring.study.university.University.repositories.StudentRepository;
import com.spring.study.university.University.services.validations.PersonValidations;
import com.spring.study.university.University.services.validations.StudentValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;
  private final GradeRepository gradeRepository;
  private final StudentValidations studentValidations;

  public Student getStudentByStudentNumber(Long studentNumber) {
    return studentValidations.validateIfStudentExists(studentNumber);
  }

  public Student createStudent(Student student) {
    studentValidations.validateStudentNoExists(student.getStudentNumber());
    studentValidations.validateStudentRole(student);

    Student studentToSave = new Student();
    studentToSave.setPhoneNumber(student.getPhoneNumber());
    studentToSave.setName(student.getName());
    studentToSave.setLastName(student.getLastName());
    studentToSave.setEmail(student.getEmail());
    studentToSave.setRole(student.getRole());
    studentToSave.setDocumentNumber(student.getDocumentNumber());
    studentToSave.setStudentNumber(student.getStudentNumber());

    studentValidations.validateStudentFields(studentToSave);

    return studentRepository.save(studentToSave);
  }

  public Student updateStudent(Long studentNumber, Student student) {
    Student student1 = studentValidations.validateIfStudentExists(studentNumber);
    if (student.getName() != null) {
      student1.setName(student.getName());
    }
    if (student.getLastName() != null) {
      student1.setLastName(student.getLastName());
    }
    if (student.getPhoneNumber() != null) {
      student1.setPhoneNumber(student.getPhoneNumber());
    }

    return studentRepository.save(student1);
  }

  public void deleteStudent(Long studentNumber) {
    Student student = studentValidations.validateIfStudentExists(studentNumber);

    studentRepository.delete(student);
  }

  public Set<Grade> getStudentGrades(Long studentNumber) {
    Student student = studentValidations.validateIfStudentExists(studentNumber);

    return new HashSet<>(gradeRepository.findAllByStudent(student));
  }
}
