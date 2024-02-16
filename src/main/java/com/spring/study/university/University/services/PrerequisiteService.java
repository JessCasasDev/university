package com.spring.study.university.University.services;

import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.services.validations.PrerequisiteValidators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PrerequisiteService {

  private final PrerequisiteValidators prerequisiteValidators;

  public void validateAssignatureAndPrerequisitesAreDifferent(List<UUID> prerequisites, Assignature assignature) {
    prerequisiteValidators.validateAssignatureAndPrerequisitesAreDifferent(prerequisites, assignature);
  }
}
