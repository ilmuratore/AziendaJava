package com.example.demo.config.validation;

import com.example.demo.repositories.PersonaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator che implementa la logica associata all’annotazione {@link ExistingPersona}.
 * Validator implementing the logic for the {@link ExistingPersona} annotation.
 *
 * <p>Verifica che l’ID di Persona non sia nullo e corrisponda a una Persona presente nel database.
 * Se personaId è null, la validazione viene considerata valida perché altre annotazioni (@NotNull) gestiranno il caso di null.</p>
 *
 * @see ExistingPersona
 */
@Component
public class ExistingPersonaValidator implements ConstraintValidator<ExistingPersona, Long> {

    private final PersonaRepository repository;

    /**
     * Costruttore con iniezione del repository.
     * Constructor with repository injection.
     *
     * @param repository repository per accedere alle entità Persona / repository for accessing Persona entities
     */
    @Autowired
    public ExistingPersonaValidator(PersonaRepository repository) {
        this.repository = repository;
    }

    /**
     * Verifica se il personaId è valido.
     * <ul>
     *   <li>Se personaId è null, restituisce true (per delegare a @NotNull eventuale errore).</li>
     *   <li>Altrimenti, verifica che esista almeno un record con quell’ID nel {@link PersonaRepository}.</li>
     * </ul>
     *
     * Checks if personaId is valid.
     * <ul>
     *   <li>If personaId is null, returns true (delegating null-check to @NotNull).</li>
     *   <li>Otherwise, checks that at least one record with that ID exists in {@link PersonaRepository}.</li>
     * </ul>
     *
     * @param personaId       ID della Persona da validare / ID of the Persona to validate
     * @param context         contesto della validazione / validation context
     * @return true se valida, false altrimenti / true if valid, false otherwise
     */
    @Override
    public boolean isValid(Long personaId, ConstraintValidatorContext context) {
        if (personaId == null) {
            // @NotNull gestirà l'errore di nullità
            // @NotNull will handle the null case
            return true;
        }
        return repository.existsById(personaId);
    }
}
