package com.spring.study.university.University.utils;

import com.spring.study.university.University.enums.Day;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DayEnumValidatorImpl implements ConstraintValidator<DayEnumValidator, Day> {
  private Day[] subset;

  @Override
  public void initialize(DayEnumValidator constraintAnnotation) {
    this.subset = constraintAnnotation.anyOf();
  }

  @Override
  public boolean isValid(Day value, ConstraintValidatorContext context) {
    return value == null || Arrays.asList(subset).contains(value);
  }
}
