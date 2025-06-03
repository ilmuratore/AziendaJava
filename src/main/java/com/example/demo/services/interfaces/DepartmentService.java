package com.example.demo.services.interfaces;

import com.example.demo.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO create(DepartmentDTO dto);
    DepartmentDTO update(Long id, DepartmentDTO dto);
    DepartmentDTO findById(Long id);
    List<DepartmentDTO> findAll();
    void delete(Long id);
}
