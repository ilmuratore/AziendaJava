package com.example.demo.services;

import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entities.Department;
import com.example.demo.entities.Persona;
import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.services.interfaces.DepartmentService;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final PersonaRepository personaRepository;
    private final EntityMapper mapper;

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            PersonaRepository personaRepository,
            EntityMapper mapper
    ) {
        this.departmentRepository = departmentRepository;
        this.personaRepository = personaRepository;
        this.mapper = mapper;
    }

    @Override
    public DepartmentDTO create(DepartmentDTO dto) {
        Persona persona = personaRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + dto.getManagerId()));
        Department newDepartment = mapper.toEntity(dto);
        newDepartment.setManager(persona);
        Department saved = departmentRepository.save(newDepartment);
        return mapper.toDto(saved);
    }

    @Override
    public DepartmentDTO update(Long id, DepartmentDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        if (dto.getName() != null) {
            department.setName(dto.getName());
        }
        if (dto.getManagerId() != null && department.getManager().getId().equals(dto.getManagerId())) {
            Persona manager = personaRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + dto.getManagerId()));
            department.setManager(manager);
        }
        if (dto.getLocation() != null) {
            department.setLocation(dto.getLocation());
        }
        Department updated = departmentRepository.save(department);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        return mapper.toDto(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}


