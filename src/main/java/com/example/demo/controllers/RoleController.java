package com.example.demo.controllers;


import com.example.demo.dto.RoleDTO;
import com.example.demo.services.RoleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {

    private final RoleServiceImpl service;

    public RoleController(RoleServiceImpl service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get by id
    @GetMapping("/id/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Long id) {
        RoleDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    //POST
    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO dto) {
        RoleDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    //PUT
    @PutMapping("/update/id/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody RoleDTO request) {
        RoleDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
