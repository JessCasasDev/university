package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.domain.DTO.GradeDTO;
import com.spring.study.university.University.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentTransactions {
  private final StudentRepository studentRepository;


  public Student createStudent(Student student) {
    Student studentToSave = new Student();
    studentToSave.setPhoneNumber(student.getPhoneNumber());
    studentToSave.setName(student.getName());
    studentToSave.setLastName(student.getLastName());
    studentToSave.setEmail(student.getEmail());
    studentToSave.setRole(student.getRole());
    studentToSave.setDocumentNumber(student.getDocumentNumber());
    studentToSave.setStudentNumber(student.getStudentNumber());

    return student;
  }

  public Student saveStudent(Student student) {
    return studentRepository.save(student);
  }

  public Optional<Student> getStudentByStudentNumber(Long studentNumber) {
    return studentRepository.findByStudentNumber(studentNumber);
  }

  public void deleteStudent(Student student) {
    studentRepository.delete(student);
  }

  public Student updateStudent(Student studentSaved, Student student) {
    if (student.getName() != null) {
      studentSaved.setName(student.getName());
    }
    if (student.getLastName() != null) {
      studentSaved.setLastName(student.getLastName());
    }
    if (student.getPhoneNumber() != null) {
      studentSaved.setPhoneNumber(student.getPhoneNumber());
    }

    return studentSaved;
  }

  public List<Grade> getGradesByStudent(Student student) {
    List<GradeDTO> grades = studentRepository.getGradesByStudentNumber(student.documentNumber);
    List<Grade> grades1 = new ArrayList<>();
    grades.forEach(
        g -> grades1.add(new Grade(g)));
    return grades1;
  }
}
