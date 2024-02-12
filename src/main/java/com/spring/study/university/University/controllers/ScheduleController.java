package com.spring.study.university.University.controllers;

import com.spring.study.university.University.domain.Schedule;
import com.spring.study.university.University.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
  private final ScheduleService scheduleService;

  @PostMapping("")
  public Schedule createSchedule(@RequestBody Schedule schedule) {
    return scheduleService.createSchedule(schedule);
  }

  @PatchMapping("/{uuid}")
  public Schedule updateSchedule(@PathVariable UUID uuid, @RequestBody Schedule schedule) {
    return scheduleService.updateSchedule(uuid, schedule);
  }

  @DeleteMapping("/{uuid}")
  public void deleteSchedule(@PathVariable UUID uuid) {
    scheduleService.deleteSchedule(uuid);
  }

  @GetMapping("")
  public List<Schedule> getSchedules() {
    return scheduleService.getSchedules();
  }
}
