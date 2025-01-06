package com.practice.spring.airbnb.customanotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EnumValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {

    String message() default "Guest gender can be either MALE , FEMALE OR OTHER";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
