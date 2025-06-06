package com.example.demo.config.validation;

import com.example.demo.repositories.RoleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


public class ExistingRolesValidator implements ConstraintValidator<ExistingRoles, Set<Long>> {

    private final RoleRepository repository;

    @Autowired
    public ExistingRolesValidator(RoleRepository repository) {
        this.repository = repository;
    }


    @Override
    public boolean isValid(Set<Long> roleIds, ConstraintValidatorContext context) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        return roleIds.stream().allMatch(repository::existsById);
    }
}
