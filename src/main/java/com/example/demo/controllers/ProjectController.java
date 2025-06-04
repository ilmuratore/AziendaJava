package com.example.demo.controllers;


import com.example.demo.dto.ProjectDTO;
import com.example.demo.services.ProjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    private final ProjectServiceImpl service;

    public ProjectController(ProjectServiceImpl service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll() {
        List<ProjectDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get by id
    @GetMapping("/id/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
        ProjectDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    //POST
    @PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO dto) {
        ProjectDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    //PUT
    @PutMapping("/update/id/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @RequestBody ProjectDTO request) {
        ProjectDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
