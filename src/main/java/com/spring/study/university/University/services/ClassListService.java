package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.services.transactions.ClassListTransactions;
import com.spring.study.university.University.services.validations.ClassListValidations;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ClassListService {
  private final ClassListTransactions classListTransactions;
  private final ClassListValidations classListValidations;
  private final AssignatureService assignatureService;
  private final ScheduleService scheduleService;
  private final StudentService studentService;


  @Transactional
  public ClassList createClassList(String assignatureId, List<String> schedules, Integer maxNumberOfStudents) {
    Assignature assignature = assignatureService.getAssignature(UUID.fromString(assignatureId));

    List<UUID> schedulesIds = schedules.stream().map(UUID::fromString).toList();

    List<Schedule> schedulesList = scheduleService.findSchedules(schedulesIds);

    ClassList classList = classListTransactions.createClassList(assignature, schedulesList, maxNumberOfStudents);

    classListValidations.validateClassListFields(classList);

    return classListTransactions.saveClassList(classList);
  }

  @Transactional
  public ClassList addStudent(UUID uuid, Long studentId) {
    ClassList classList = getClassList(uuid);

    Student student = studentService.getStudent(studentId);

    classListValidations.validateIfClassIsFull(classList);
    classListValidations.validateIfStudentInClass(classList, student);
    assignatureService.getPrerequisitesValidations(classList.getAssignature(), student);

    classList.getStudents().add(student);

    return classListTransactions.saveClassList(classList);
  }

  @Transactional
  public ClassList removeStudent(UUID uuid, Long studentId) {
    ClassList classList = getClassList(uuid);
    Student student = studentService.getStudent(studentId);

    classListValidations.validateIfStudentInClass(classList, student);

    classList.getStudents().remove(student);

    return classListTransactions.saveClassList(classList);
  }

  public List<Student> getStudentsOfClass(UUID uuid) {
    ClassList classList = getClassList(uuid);

    return new ArrayList<>(classList.getStudents());

  }

  public ClassList getClassList(UUID uuid) {
    Optional<ClassList> classListOptional = classListTransactions.getClassListById(uuid);
    return classListValidations.validateIfClassListExists(classListOptional);
  }
}
