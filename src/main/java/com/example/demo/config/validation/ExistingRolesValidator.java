package com.example.demo.config.validation;

import com.example.demo.repositories.RoleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Validator che implementa la logica associata all’annotazione {@link ExistingRoles}.
 * Validator implementing the logic for the {@link ExistingRoles} annotation.
 *
 * <p>Verifica che il Set<Long> di roleIds non sia null/empty e che ogni ID corrisponda a un Role esistente nel database.
 * Se roleIds è null o vuoto, la validazione viene considerata valida perché altre annotazioni (@NotEmpty, @NotNull) gestiranno il caso.</p>
 *
 * @see ExistingRoles
 */
public class ExistingRolesValidator implements ConstraintValidator<ExistingRoles, Set<Long>> {

    private final RoleRepository repository;

    /**
     * Costruttore con iniezione del repository.
     * Constructor with repository injection.
     *
     * @param repository repository per accedere alle entità Role / repository for accessing Role entities
     */
    @Autowired
    public ExistingRolesValidator(RoleRepository repository) {
        this.repository = repository;
    }

    /**
     * Verifica se tutti i roleIds nel Set esistono.
     * <ul>
     *   <li>Se roleIds è null o vuoto, restituisce true (per delegare a @NotEmpty eventuale errore).</li>
     *   <li>Altrimenti, verifica che ogni ID esista nel {@link RoleRepository}.</li>
     * </ul>
     *
     * Checks if all roleIds in the Set exist.
     * <ul>
     *   <li>If roleIds is null or empty, returns true (delegating empty-check to @NotEmpty).</li>
     *   <li>Otherwise, checks that each ID exists in {@link RoleRepository}.</li>
     * </ul>
     *
     * @param roleIds         Insieme di ID dei Role da validare / Set of Role IDs to validate
     * @param context         contesto della validazione / validation context
     * @return true se tutti gli ID esistono, false altrimenti / true if all IDs exist, false otherwise
     */
    @Override
    public boolean isValid(Set<Long> roleIds, ConstraintValidatorContext context) {
        if (roleIds == null || roleIds.isEmpty()) {
            // @NotEmpty gestirà l'errore di vuoto o null
            // @NotEmpty will handle the empty or null case
            return true;
        }
        return roleIds.stream().allMatch(repository::existsById);
    }
}
