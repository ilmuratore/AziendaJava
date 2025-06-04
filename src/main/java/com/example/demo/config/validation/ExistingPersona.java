package com.example.demo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotazione di validazione per verificare l’esistenza di una Persona.
 * Validation annotation to check the existence of a Persona.
 *
 * <p>Utilizzata sui campi Long che rappresentano l’ID di una Persona.
 * Restituisce errore se l’ID non corrisponde a nessuna Persona nel repository.</p>
 *
 * <p>Can be placed on fields only.</p>
 *
 * <p>Messaggio di default:
 * "Persona non trovata | Persona not found"</p>
 *
 * @see ExistingPersonaValidator
 */
@Documented
@Constraint(validatedBy = ExistingPersonaValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ExistingPersona {

    /**
     * Messaggio di errore visualizzato se la validazione fallisce.
     * Default error message shown if validation fails.
     *
     * @return il messaggio di errore
     *         the error message
     */
    String message() default "Persona non trovata | Persona not found";

    /**
     * Gruppi di validazione a cui appartiene questa annotazione.
     * Validation groups to which this annotation belongs.
     *
     * @return i gruppi di validazione
     *         the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload che può essere associato al constraint.
     * Payload that can be associated with the constraint.
     *
     * @return il payload
     *         the payload
     */
    Class<? extends Payload>[] payload() default {};
}
