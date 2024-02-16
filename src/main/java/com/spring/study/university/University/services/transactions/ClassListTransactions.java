package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.domain.ClassList;
import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.repositories.ClassListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ClassListTransactions {
  private final ClassListRepository classListRepository;

  public ClassList createClassList(Assignature assignature, List<Schedule> schedulesList, Integer maxNumberOfStudents) {
    ClassList classList = new ClassList();
    classList.setAssignature(assignature);
    classList.getSchedules().addAll(schedulesList);
    classList.setMaxNumberOfStudents(maxNumberOfStudents);


    return classList;
  }

  public ClassList saveClassList(ClassList classList){
    return classListRepository.save(classList);
  }

  public Optional<ClassList> getClassListById(UUID uuid){
    return classListRepository.findById(uuid);
  }

}
