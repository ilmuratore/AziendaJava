package com.example.demo.config.validation;

import com.example.demo.entities.Permission;
import com.example.demo.repositories.PermissionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniquePermissionNameValidator implements ConstraintValidator<UniquePermissionName, String>{

    private final PermissionRepository repository;

    @Autowired
    public UniquePermissionNameValidator(PermissionRepository repository){ this.repository = repository;}

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.isBlank()) {
            return true;
        }
        return !repository.existsByName(name);
    }
}
