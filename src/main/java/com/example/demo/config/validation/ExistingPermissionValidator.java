package com.example.demo.config.validation;

import com.example.demo.repositories.PermissionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class ExistingPermissionValidator implements ConstraintValidator<ExistingPermission, Long> {

    private final PermissionRepository repository;

    public ExistingPermissionValidator(PermissionRepository repository){ this.repository = repository;}

    @Override
    public boolean isValid(Long permissionId, ConstraintValidatorContext context){
        if(permissionId == null){
            return true;
        }
        return repository.existsById(permissionId);
    }
}
