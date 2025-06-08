package com.example.demo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ExistingDepartmentValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ExistingDepartment {

    String message() default "Dipartimento non trovato | Department not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
