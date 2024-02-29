package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.services.transactions.StudentTransactions;
import com.spring.study.university.University.services.validations.StudentValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  public static final long STUDENT_NUMBER = 1L;

  @InjectMocks
  StudentService studentService;

  @Mock
  StudentTransactions studentTransactions;
  @Mock
  StudentValidations studentValidations;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    studentService = new StudentService(studentTransactions, studentValidations);
  }

  @Test
  void createStudent() {
    Student student = getStudent1();

    when(studentTransactions.createStudent(student)).thenReturn(student);
    when(studentTransactions.saveStudent(student)).thenReturn(student);

    assertNotNull(studentService.createStudent(student));
  }

  @Test
  void noCreateStudentIfStudentExists() {
    Student student = getStudent1();
    when(studentTransactions.createStudent(student)).thenReturn(student);
    when(studentTransactions.saveStudent(student)).thenReturn(student);
    studentService.createStudent(student);

    ResponseStatusException STUDENT_ALREADY_EXISTS =
        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already exists");

    doThrow(STUDENT_ALREADY_EXISTS)
        .when(studentValidations)
        .validateStudentNoExists(student.getStudentNumber());

    Assertions.assertThrows(ResponseStatusException.class, () -> {
      studentService.createStudent(student);
    });

    verify(studentValidations, times(2))
        .validateStudentNoExists(student.getStudentNumber());
  }

  @Test
  void noCreateStudentIfRoleInvalid() {
    Student student = getStudent1();
    Role role = new Role(RoleEnum.Admin);
    student.setRole(role);

    ResponseStatusException INVALID_ROLE =
        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Role");

    doThrow(INVALID_ROLE)
        .when(studentValidations)
        .validateStudentRole(student);

    Assertions.assertThrows(ResponseStatusException.class, () -> {
      studentService.createStudent(student);
    });

    verify(studentValidations)
        .validateStudentRole(student);
  }

  @Test
  void updateStudent() {
    long PHONE_NUMBER = 98765L;
    String NAME = "Otro nombre";
    String LAST_NAME = "Otro apellido";

    Student student = getStudent1();
    when(studentService.getStudent(student.getStudentNumber())).thenReturn(student);

    Student studentUpdated = new Student();
    studentUpdated.setUuid(student.getUuid());
    studentUpdated.setPhoneNumber(PHONE_NUMBER);
    studentUpdated.setRole(student.getRole());
    studentUpdated.setStudentNumber(student.getStudentNumber());
    studentUpdated.setEmail(student.getEmail());
    studentUpdated.setName(NAME);
    studentUpdated.setLastName(LAST_NAME);
    studentUpdated.setDocumentNumber(student.getDocumentNumber());
    when(studentTransactions.updateStudent(student, studentUpdated)).thenReturn(studentUpdated);
    when(studentTransactions.saveStudent(studentUpdated)).thenReturn(studentUpdated);

    Student studentResult = studentService.updateStudent(studentUpdated.getStudentNumber(), studentUpdated);
    assertNotNull(studentResult);
    assertEquals(student.getStudentNumber(), studentUpdated.getStudentNumber());
    assertEquals(LAST_NAME, studentUpdated.getLastName());
    assertEquals(NAME, studentUpdated.getName());

  }

  @Test
  void deleteStudent() {
    Student student = getStudent1();
    when(studentService.getStudent(student.getStudentNumber())).thenReturn(student);

    studentService.deleteStudent(student.getStudentNumber());
    verify(studentTransactions).deleteStudent(student);
  }

  @Test
  void getStudent() {
    Optional<Student> student = Optional.of(getStudent1());
    when(studentTransactions.getStudentByStudentNumber(
        student.get().getStudentNumber()))
        .thenReturn(student);

    studentService.getStudent(student.get().getStudentNumber());
    verify(studentValidations).validateIfStudentExists(student);
  }

  @Test
  void getStudentGrades() {
    Student student = getStudent1();
    Grade grade = new Grade();
    grade.setStudent(student);
    Assignature assignature = new Assignature();
    assignature.setName("1");
    assignature.setCredits(1);
    grade.setAssignature(assignature);
    List<Grade> result = new ArrayList<>();
    result.add(grade);
    student.setGrades(result);

    when(studentService.getStudent(student.getStudentNumber())).thenReturn(student);

    List<Grade> grades = studentService.getStudentGrades(student.getStudentNumber());

    assertTrue(grades.contains(result.getFirst()));
    assertEquals(grades.size(), result.size());
  }


  private Student getStudent1() {
    Student student = new Student();
    student.setLastName("Student1");
    student.setStudentNumber(STUDENT_NUMBER);
    student.setName("Roberto");
    student.setDocumentNumber(123L);
    student.setPhoneNumber(123L);
    student.setUuid(UUID.randomUUID());
    student.setEmail("email@email.com");
    return student;
  }
}
