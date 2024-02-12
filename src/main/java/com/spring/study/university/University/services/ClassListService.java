package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.ClassListRepository;
import com.spring.study.university.University.services.validations.AssignatureValidations;
import com.spring.study.university.University.services.validations.ClassListValidations;
import com.spring.study.university.University.services.validations.ScheduleValidations;
import com.spring.study.university.University.services.validations.StudentValidations;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ClassListService {
  private final ClassListRepository classListRepository;
  private final AssignatureValidations assignatureValidations;
  private final ScheduleValidations scheduleValidations;

  private final ClassListValidations classListValidations;
  private final StudentValidations studentValidations;

  public ClassList createClassList(String assignatureId, List<String> schedules, Integer maxNumberOfStudents) {
    Assignature assignature = assignatureValidations
        .validateIfAssignatureExists(UUID.fromString(assignatureId));

    List<UUID> schedulesIds = schedules.stream().map(id -> UUID.fromString(id)).toList();

    List<Schedule> schedulesList = scheduleValidations.validateSchedulesExists(schedulesIds);

    ClassList classList = new ClassList();
    classList.setAssignature(assignature);
    classList.getSchedules().addAll(schedulesList);
    classList.setMaxNumberOfStudents(maxNumberOfStudents);

    classListValidations.validateClassListFields(classList);

    return classListRepository.save(classList);
  }

  public ClassList addStudent(UUID uuid, Long studentId) {
    ClassList classList = classListValidations.validateIfClassListExists(uuid);

    Student student = studentValidations.validateIfStudentExists(studentId);

    classListValidations.validateIfClassIsFull(classList);
    classListValidations.validateIfStudentInClass(classList, student);
    assignatureValidations.validateAssignaturePrerequisiteValid(classList.getAssignature(), student);

    classList.getStudents().add(student);

    return classListRepository.save(classList);
  }

  public ClassList removeStudent(UUID uuid, Long studentId) {
    ClassList classList = classListValidations.validateIfClassListExists(uuid);
    Student student = studentValidations.validateIfStudentExists(studentId);

    classListValidations.validateIfStudentInClass(classList, student);

    classList.getStudents().remove(student);

    return classListRepository.save(classList);
  }

  public List<Student> getStudentsOfClass(UUID uuid) {
    ClassList classList = classListValidations.validateIfClassListExists(uuid);

    List<Student> students = new ArrayList<>();
    classList.getStudents().forEach(students::add);

    return students;
  }

  public ClassList getClassList(UUID uuid) {
    return classListValidations.validateIfClassListExists(uuid);
  }
}
