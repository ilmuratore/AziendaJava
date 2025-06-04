package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entities.Persona;
import com.example.demo.entities.Project;
import com.example.demo.entities.Task;
import com.example.demo.repositories.PersonaRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.services.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PersonaRepository personaRepository;
    private final ProjectRepository projectRepository;
    private final EntityMapper mapper;

    @Override
    public TaskDTO create(TaskDTO dto) {
        Task task = mapper.toEntity(dto);
        if (dto.getAssignedToId() != null) {
            Persona assignedTo = personaRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + dto.getAssignedToId()));
            task.setAssignedTo(assignedTo);
        }
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + dto.getProjectId()));
            task.setProject(project);
        }
        Task entity = taskRepository.save(task);
        return mapper.toDto(entity);
    }

    @Override
    public TaskDTO update(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id:" + id));
        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getDueDate() != null) {
            task.setDueDate(dto.getDueDate());
        }
        if (dto.getStatus() != null) {
            task.setDueDate(dto.getDueDate());
        }
        if (dto.getAssignedToId() != null) {
            Persona assignedTo = personaRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + dto.getAssignedToId()));
            task.setAssignedTo(assignedTo);
        } else {
            task.setAssignedTo(null);
        }
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + dto.getProjectId()));
            task.setProject(project);
        } else {
            task.setProject(null);
        }
        Task updated = taskRepository.save(task);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id:" + id));
        return mapper.toDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
