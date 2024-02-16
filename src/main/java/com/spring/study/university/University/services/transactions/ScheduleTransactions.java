package com.spring.study.university.University.services.transactions;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleTransactions {
  private final ScheduleRepository scheduleRepository;

  public Schedule saveSchedule(Schedule schedule) {
    return scheduleRepository.save(schedule);
  }

  public List<Schedule> findSchedules(List<UUID> list) {
    return (List<Schedule>) scheduleRepository.findAllById(list);
  }

  public List<Schedule> getSchedules() {
    List<Schedule> schedules = new ArrayList<>();
    scheduleRepository.findAll().forEach(schedules::add);
    return schedules;
  }

  public Optional<Schedule> getSchedule(UUID uuid) {
    return scheduleRepository.findById(uuid);
  }

  public void deleteSchedule(Schedule schedule) {
    scheduleRepository.delete(schedule);
  }

  public Schedule editSchedule(Schedule scheduleSaved, Schedule schedule) {
    if (schedule.getDay() != null) {
      scheduleSaved.setDay(schedule.getDay());
    }
    if (schedule.getInitialTime() != null) {
      scheduleSaved.setInitialTime(schedule.getInitialTime());
    }
    if (schedule.getEndTime() != null) {
      scheduleSaved.setEndTime(schedule.getEndTime());
    }

    return scheduleSaved;
  }

  public Optional<Schedule> findByDayAndInitialTimeAndEndTime(Schedule schedule) {
    return scheduleRepository.findByDayAndInitialTimeAndEndTime(
        schedule.getDay(),
        schedule.getInitialTime(),
        schedule.getEndTime());
  }
}
