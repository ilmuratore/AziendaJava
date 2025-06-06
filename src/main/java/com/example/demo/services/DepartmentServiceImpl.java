package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.request.CreateDepartmentRequestDTO;
import com.example.demo.dto.request.UpdateDepartmentRequestDTO;
import com.example.demo.dto.response.DepartmentResponseDTO;
import com.example.demo.entities.Department;
import com.example.demo.entities.Persona;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.PersonaRepository;
import com.example.demo.services.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final PersonaRepository personaRepository;
    private final EntityMapper mapper;

    @Override
    public DepartmentResponseDTO createDepartment(CreateDepartmentRequestDTO request) {
        Department newDepartment = new Department();
        newDepartment.setName(request.getName().trim());
        newDepartment.setLocation(request.getLocation() != null ? request.getLocation().trim() : null);
        if (request.getManagerId() != null) {
            Persona manager = personaRepository.findById(request.getManagerId()).orElseThrow(() -> new EntityNotFoundException("Persona (manager) not found with id: " + request.getManagerId()));
            newDepartment.setManager(manager);
        }
        Department saved = departmentRepository.save(newDepartment);
        return mapper.toDepartmentResponseDto(saved);
    }

    @Override
    public DepartmentResponseDTO updateDepartment(Long id, UpdateDepartmentRequestDTO request) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        if (request.getName() != null && !request.getName().isBlank()) {
            department.setName(request.getName().trim());
        }
        if (request.getLocation() != null) {
            department.setLocation(request.getLocation().trim());
        }
        if (request.getManagerId() != null) {
            Persona manager = personaRepository.findById(request.getManagerId()).orElseThrow(() -> new EntityNotFoundException("Persona (manager) not found with id: " + request.getManagerId()));
            department.setManager(manager);
        } else if (request.getManagerId() != null && request.getManagerId() == 0) {
            department.setManager(null);
        }
        Department updated = departmentRepository.save(department);
        return mapper.toDepartmentResponseDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        return mapper.toDepartmentResponseDto(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department non trovato con id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(mapper::toDepartmentResponseDto);
    }


}


