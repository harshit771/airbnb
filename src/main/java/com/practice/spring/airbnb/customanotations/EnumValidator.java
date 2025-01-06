package com.practice.spring.airbnb.customanotations;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValue, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> gender = List.of("MALE","FEMALE","OTHER");
        return gender.contains(value);
    }

}
