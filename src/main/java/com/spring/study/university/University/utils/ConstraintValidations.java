package com.spring.study.university.University.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@AllArgsConstructor
public class ConstraintValidations {
  private final Validator validator;

  public void validateFields(Object obj) {
    Set<ConstraintViolation<Object>> violations = validator.validate(obj);

    if (!violations.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (ConstraintViolation<Object> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred: " + sb.toString());
    }
  }
}
