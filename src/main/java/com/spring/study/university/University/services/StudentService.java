package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Person;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.GradeRepository;
import com.spring.study.university.University.repositories.PersonRepository;
import com.spring.study.university.University.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;
  private final GradeRepository gradeRepository;
  private final PersonRepository personRepository;


  public Student getStudentByStudentNumber(Long studentNumber) {
    return studentRepository
        .findByStudentNumber(studentNumber)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND) {
        });
  }

  public Student createStudent(UUID personId, Long studentId) {
    Person person = personRepository.findById(personId).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND));

    Optional<Student> student = studentRepository
        .findByDocumentNumber(studentId);

    if (student.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already exists");
    }

    if (!person.getRole().getRole().name().equals(RoleEnum.Student.name())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Student studentToSave = new Student();
    studentToSave.setStudentNumber(studentId);
    studentToSave.setPerson(person);
    return studentRepository.save(studentToSave);
  }

  public Student updateStudent(Long studentNumber, Student student) {
    Student student1 = studentRepository.findByStudentNumber(studentNumber)
        .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND)));

    return studentRepository.save(student1);
  }

  public void deleteStudent(Long studentNumber) {
    Student student = studentRepository.findByStudentNumber(studentNumber)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    studentRepository.delete(student);
  }

  public Set<Grade> getStudentGrades(Long studentNumber) {
    Student student = studentRepository.findByStudentNumber(studentNumber)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not Found"));

    Set<Grade> grades = new HashSet<>();

    gradeRepository.findAllByStudent(student).forEach(grade -> grades.add(grade));

    return grades;
  }
}
