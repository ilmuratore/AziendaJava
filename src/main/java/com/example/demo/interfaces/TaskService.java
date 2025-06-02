package com.example.demo.interfaces;

import com.example.demo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO create(TaskDTO dto);
    TaskDTO update(Long id, TaskDTO dto);
    TaskDTO findById(Long id);
    List<TaskDTO> findAll();
    void delete(Long id);
}
