package com.example.demo.controllers;

import com.example.demo.dto.PermissionDTO;
import com.example.demo.services.interfaces.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permission")
public class PermissionController {
    
    private final PermissionService service;


    public PermissionController(PermissionService service) {
        this.service = service;
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getAll(){
        List<PermissionDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //GET ID
    @GetMapping("/id/{id}")
    public ResponseEntity<PermissionDTO> getById(@PathVariable Long id){
        PermissionDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    //POST
    @PostMapping
    public ResponseEntity<PermissionDTO> create(@RequestBody PermissionDTO dto){
        PermissionDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    //PUT
    @PutMapping("/update/id/{id}")
    public ResponseEntity<PermissionDTO> update(@PathVariable Long id, @RequestBody PermissionDTO request){
        PermissionDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //DELETE
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
