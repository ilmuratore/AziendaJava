package com.example.demo.config.validation;

import com.example.demo.repositories.DepartmentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class ExistingDepartmentValidator implements ConstraintValidator<ExistingDepartment, Long > {

    private final DepartmentRepository repository;


    public ExistingDepartmentValidator(DepartmentRepository repository){ this.repository = repository;}

    @Override
    public boolean isValid(Long departmentId, ConstraintValidatorContext context){
        if(departmentId == null){
            return true;
        }
        return repository.existsById(departmentId);
    }
}
