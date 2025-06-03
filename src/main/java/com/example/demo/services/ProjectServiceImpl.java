package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.entities.Project;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.services.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EntityMapper mapper;

    @Override
    public ProjectDTO create(ProjectDTO dto){
        Project newProject = mapper.toEntity(dto);
        Project saved = projectRepository.save(newProject);
        return mapper.toDto(saved);
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTO dto){
        Project project = projectRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Project not found with id: " + id));
        if(dto.getCode() != null){
            project.setCode(dto.getCode());
        }
        if(dto.getName() != null){
            project.setName(dto.getName());
        }
        if(dto.getDescription() != null){
            project.setDescription(dto.getDescription());
        }
        if(dto.getStartDate() != null){
            project.setStartDate(dto.getStartDate());
        }
        if(dto.getEndDate() != null){
            project.setEndDate(dto.getEndDate());
        }
        if(dto.getStatus() != null){
            project.setStatus(dto.getStatus());
        }
        Project updated = projectRepository.save(project);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findById(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow( ()-> new EntityNotFoundException("Project not found with id: " + id));
        return mapper.toDto(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> findAll(){
        return projectRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){
        if(!projectRepository.existsById(id)){
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
