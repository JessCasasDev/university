package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Grade;
import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.domain.Student;
import com.spring.study.university.University.repositories.AssignatureRepository;
import com.spring.study.university.University.repositories.ClassListRepository;
import com.spring.study.university.University.repositories.ScheduleRepository;
import com.spring.study.university.University.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ClassListService {
  private final ClassListRepository classListRepository;
  private final StudentRepository studentRepository;
  private final AssignatureRepository assignatureRepository;
  private final ScheduleRepository scheduleRepository;

  public ClassList createClassList(String assignatureId, List<String> schedules, Integer maxNumberOfStudents) {
    Assignature assignature = assignatureRepository
        .findById(UUID.fromString(assignatureId))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    List<UUID> schedulesIds = schedules.stream().map(id -> UUID.fromString(id)).toList();

    List<Schedule> schedulesList = (List<Schedule>) scheduleRepository.findAllById(schedulesIds);

    ClassList classList = new ClassList();
    classList.setAssignature(assignature);
    classList.getSchedules().addAll(schedulesList);
    classList.setMaxNumberOfStudents(maxNumberOfStudents);

    return classListRepository.save(classList);
  }

  public ClassList addStudent(UUID uuid, Long studentId) {
    ClassList classList = classListRepository
        .findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

    Student student = studentRepository
        .findByStudentNumber(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (classList.getStudents().size() == classList.getMaxNumberOfStudents()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No more students accepted");
    }

    if (classList.getStudents().contains(student)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already in class");
    }

    validateAssignaturePrerequisites(classList.getAssignature(), student);

    classList.getStudents().add(student);

    return classListRepository.save(classList);
  }

  public ClassList removeStudent(UUID uuid, Long studentId) {
    ClassList classList = classListRepository
        .findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    Student student = studentRepository
        .findByStudentNumber(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!classList.getStudents().contains(student)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student is not in class");
    }

    classList.getStudents().remove(student);

    return classListRepository.save(classList);
  }

  public List<Student> getStudentsOfClass(UUID uuid) {
    ClassList classList = classListRepository
        .findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    List<Student> students = new ArrayList<>();

    classList.getStudents().forEach(student -> students.add(student));

    return students;
  }

  private void validateAssignaturePrerequisites(Assignature assignature, Student student) {
    if (!assignature.getPrerequisites().isEmpty()) {

      List<Assignature> prerequsites = new ArrayList<>(assignature.getPrerequisites()
          .stream().map(prerequisite -> prerequisite.getAssignature()).toList());

      List<Grade> studentAssignatures = student
          .getGrades()
          .stream()
          .filter(grade -> prerequsites.contains(grade.getAssignature())).toList();

      if (studentAssignatures.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prerequisite required");
      }

      if (studentAssignatures.stream().noneMatch(grade -> grade.getGrade() >= Float.valueOf(3))) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prerequisite needs at least 3.0");
      }
    }
  }

  public ClassList getClassList(UUID uuid) {
    return classListRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
