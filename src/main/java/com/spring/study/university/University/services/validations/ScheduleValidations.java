package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ScheduleValidations {

  public List<Schedule> validateSchedulesExists(List<Schedule> schedules) {

    if (schedules.size() != 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no schedules found");
    }

    return schedules;
  }

  public Schedule validateIfScheduleExists(Optional<Schedule> schedule) {
    return schedule
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
  }

  public void validateScheduleData(Optional<Schedule> schedule) {
    if (schedule.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedule already exists");
    }
  }
}
