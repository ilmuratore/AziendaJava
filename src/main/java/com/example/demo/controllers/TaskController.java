package com.example.demo.controllers;

import com.example.demo.dto.TaskDTO;
import com.example.demo.services.interfaces.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/task")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get one by id
    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        TaskDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }


    //POST
    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto) {
        TaskDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }


    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO request) {
        TaskDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
