package com.example.demo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ExistingPermissionValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ExistingPermission {

    String message() default "Permesso non trovato | Permission not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
