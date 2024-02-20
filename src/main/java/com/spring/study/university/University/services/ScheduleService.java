package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.services.transactions.ScheduleTransactions;
import com.spring.study.university.University.services.validations.ScheduleValidations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleService {

  private final ScheduleTransactions scheduleTransactions;
  private final ScheduleValidations scheduleValidations;

  @Transactional
  public Schedule createSchedule(Schedule schedule) {
    Optional<Schedule> scheduleOptional = scheduleTransactions.findByDayAndInitialTimeAndEndTime(schedule);
    scheduleValidations.validateScheduleData(scheduleOptional);
    return scheduleTransactions.saveSchedule(schedule);
  }

  @Transactional
  public void deleteSchedule(UUID uuid) {
    Schedule schedule = getSchedule(uuid);
    scheduleTransactions.deleteSchedule(schedule);
  }

  private Schedule getSchedule(UUID uuid) {
    Optional<Schedule> scheduleOptional = scheduleTransactions.getSchedule(uuid);
    return scheduleValidations.validateIfScheduleExists(scheduleOptional);
  }

  @Transactional
  public Schedule updateSchedule(UUID uuid, Schedule schedule) {
    Schedule scheduleSaved = getSchedule(uuid);

    Schedule scheduleEdited = scheduleTransactions.editSchedule(scheduleSaved, schedule);
    Optional<Schedule> scheduleOptional = scheduleTransactions.findByDayAndInitialTimeAndEndTime(scheduleEdited);
    scheduleValidations.validateScheduleData(scheduleOptional);
    return scheduleTransactions.saveSchedule(scheduleEdited);
  }

  public List<Schedule> getSchedules() {
    return scheduleTransactions.getSchedules();
  }

  public List<Schedule> findSchedules(List<UUID> list) {
    List<Schedule> schedules = scheduleTransactions.findSchedules(list);
    return scheduleValidations.validateSchedulesExists(schedules);
  }
}
