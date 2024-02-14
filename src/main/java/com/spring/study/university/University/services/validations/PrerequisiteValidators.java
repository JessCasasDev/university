package com.spring.study.university.University.services.validations;

import com.spring.study.university.University.domain.Assignature;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PrerequisiteValidators {
  public void validateAssignatureAndPrerequisitesAreDifferent(List<UUID> prerequisites, Assignature assignature) {
    prerequisites
        .stream()
        .filter(assignature.getUuid()::equals)
        .findAny()
        .ifPresent(s -> {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignature cannot be part of prerequisites");
        });
  }
}
