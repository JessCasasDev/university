package com.spring.study.university.University.repositories;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.enums.Day;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends CrudRepository<Schedule, UUID> {

  Optional<Schedule> findByDayAndInitialTimeAndEndTime(Day day, LocalTime initialTime, LocalTime endTime);
}
