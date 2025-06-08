package com.example.demo.config.validation;

import com.example.demo.repositories.PersonaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;


@Component
public class ExistingPersonaValidator implements ConstraintValidator<ExistingPersona, Long> {

    private final PersonaRepository repository;

    public ExistingPersonaValidator(PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Long personaId, ConstraintValidatorContext context) {
        if (personaId == null) {
            return true;
        }
        return repository.existsById(personaId);
    }
}
