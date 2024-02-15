package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleValidations {

  private final ScheduleRepository scheduleRepository;

  public List<Schedule> validateSchedulesExists(List<UUID> uuids) {
    List<Schedule> schedulesList = (List<Schedule>) scheduleRepository.findAllById(uuids);

    if (uuids.size() != schedulesList.size()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no schedules found");
    }

    return schedulesList;
  }

  public Schedule validateIfScheduleExists(UUID uuid) {
    return scheduleRepository
        .findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
  }

  public void validateScheduleData(Schedule schedule){
    if (scheduleRepository.findByDayAndInitialTimeAndEndTime(
        schedule.getDay(),
        schedule.getInitialTime(),
        schedule.getEndTime()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedule already exists");
    }
  }
}
