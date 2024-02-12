package com.spring.study.university.University.utils;

import com.spring.study.university.University.enums.RoleEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RoleEnumValidatorImpl implements ConstraintValidator<RoleEnumValidator, RoleEnum> {
  private RoleEnum[] set;

  @Override
  public void initialize(RoleEnumValidator constraintAnnotation) {
    set = constraintAnnotation.anyOf();
  }

  @Override
  public boolean isValid(RoleEnum value, ConstraintValidatorContext context) {
    return value == null || Arrays.asList(set).contains(value);
  }
}
