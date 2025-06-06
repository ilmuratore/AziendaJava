package com.example.demo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = ExistingPersonaValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ExistingPersona {

    String message() default "Persona non trovata | Persona not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
