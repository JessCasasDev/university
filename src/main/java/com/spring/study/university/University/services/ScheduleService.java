package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;

  public Schedule createSchedule(Schedule schedule) {
    validateExistingSchedule(schedule);
    return scheduleRepository.save(schedule);
  }

  public void deleteSchedule(UUID uuid) {
    Schedule schedule = scheduleRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    scheduleRepository.delete(schedule);
  }

  public Schedule updateSchedule(UUID uuid, Schedule schedule) {
    Schedule scheduleSaved = scheduleRepository
        .findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (schedule.getDay() != null) {
      scheduleSaved.setDay(schedule.getDay());
    }
    if (schedule.getInitialTime() != null) {
      scheduleSaved.setInitialTime(schedule.getInitialTime());
    }
    if (schedule.getEndTime() != null) {
      scheduleSaved.setEndTime(schedule.getEndTime());
    }

    validateExistingSchedule(scheduleSaved);
    return scheduleRepository.save(scheduleSaved);
  }

  private void validateExistingSchedule(Schedule schedule) {
    if (scheduleRepository.findByDayAndInitialTimeAndEndTime(
        schedule.getDay(),
        schedule.getInitialTime(),
        schedule.getEndTime()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedule already exists");
    }
  }

  public List<Schedule> getSchedules() {
    List<Schedule> schedules = new ArrayList<>();
    scheduleRepository.findAll().forEach(schedule -> schedules.add(schedule));
    return schedules;
  }
}
