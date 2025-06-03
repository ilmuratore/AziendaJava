package com.example.demo.services.interfaces;

import com.example.demo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
   ProjectDTO create(ProjectDTO dto);
   ProjectDTO update(Long id, ProjectDTO dto);
   ProjectDTO findById(Long id);
    List<ProjectDTO> findAll();
    void delete(Long id);
}
