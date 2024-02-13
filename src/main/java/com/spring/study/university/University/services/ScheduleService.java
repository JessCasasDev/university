package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.repositories.ScheduleRepository;
import com.spring.study.university.University.services.validations.ScheduleValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final ScheduleValidations scheduleValidations;

  public Schedule createSchedule(Schedule schedule) {
    scheduleValidations.validateScheduleData(schedule);
    return scheduleRepository.save(schedule);
  }

  public void deleteSchedule(UUID uuid) {
    Schedule schedule = scheduleValidations.validateIfScheduleExists(uuid);
    scheduleRepository.delete(schedule);
  }

  public Schedule updateSchedule(UUID uuid, Schedule schedule) {
    Schedule scheduleSaved = scheduleValidations.validateIfScheduleExists(uuid);

    if (schedule.getDay() != null) {
      scheduleSaved.setDay(schedule.getDay());
    }
    if (schedule.getInitialTime() != null) {
      scheduleSaved.setInitialTime(schedule.getInitialTime());
    }
    if (schedule.getEndTime() != null) {
      scheduleSaved.setEndTime(schedule.getEndTime());
    }

    scheduleValidations.validateScheduleData(scheduleSaved);
    return scheduleRepository.save(scheduleSaved);
  }

  public List<Schedule> getSchedules() {
    List<Schedule> schedules = new ArrayList<>();
    scheduleRepository.findAll().forEach(schedule -> schedules.add(schedule));
    return schedules;
  }
}
