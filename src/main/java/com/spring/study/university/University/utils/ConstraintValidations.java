package com.spring.study.university.University.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConstraintValidations {
  private final Validator validator;

  public void validateFields(Object obj) {
    //for (ConstraintViolation<Object> constraintViolation : violations) {
    //        sb.append(constraintViolation.getMessage());
    //      }
    StringBuilder sb = new StringBuilder();
    Set<ConstraintViolation<Object>> validators = validator.validate(obj);
    if(validators.size()>0) {
      validators.stream()
          .map(i -> sb.append(" ").append(i.getMessage()))
          .filter(x -> sb.length() == validators.size())
          .findAny()
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errors occurred: " + sb));
    }

  }
}
