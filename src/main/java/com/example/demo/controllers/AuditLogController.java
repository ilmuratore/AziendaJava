package com.example.demo.controllers;


import com.example.demo.dto.AuditLogDTO;
import com.example.demo.services.AuditLogServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auditLog")
public class AuditLogController {

    private final AuditLogServiceImpl service;

    public AuditLogController(AuditLogServiceImpl service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAll() {
        List<AuditLogDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get by id
    @GetMapping("/id/{id}")
    public ResponseEntity<AuditLogDTO> getById(@PathVariable Long id) {
        AuditLogDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    //POST
    @PostMapping
    public ResponseEntity<AuditLogDTO> create(@RequestBody AuditLogDTO dto) {
        AuditLogDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    //PUT
    @PutMapping("/update/id/{id}")
    public ResponseEntity<AuditLogDTO> update(@PathVariable Long id, @RequestBody AuditLogDTO request) {
        AuditLogDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
