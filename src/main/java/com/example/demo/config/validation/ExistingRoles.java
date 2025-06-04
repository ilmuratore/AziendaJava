package com.example.demo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotazione di validazione per verificare l’esistenza di uno o più Role.
 * Validation annotation to check the existence of one or more Roles.
 *
 * <p>Utilizzata sui campi Set<Long> che rappresentano ID di Role.
 * Restituisce errore se almeno uno degli ID non corrisponde a un Role esistente nel repository.</p>
 *
 * <p>Can be placed on fields only.</p>
 *
 * <p>Messaggio di default:
 * "Non esistono roleIds | roleIds not exists"</p>
 *
 * @see ExistingRolesValidator
 */
@Documented
@Constraint(validatedBy = ExistingRolesValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ExistingRoles {

    /**
     * Messaggio di errore visualizzato se la validazione fallisce.
     * Default error message shown if validation fails.
     *
     * @return il messaggio di errore
     *         the error message
     */
    String message() default "Non esistono roleIds | roleIds not exists";

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
