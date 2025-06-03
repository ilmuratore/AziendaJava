package com.example.demo.controllers;

import com.example.demo.dto.PositionDTO;
import com.example.demo.services.interfaces.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/position")
public class PositionController {

    private final PositionService service;

    public PositionController(PositionService service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAll() {
        List<PositionDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get one by id
    @GetMapping("/id/{id}")
    public ResponseEntity<PositionDTO> getById(@PathVariable Long id) {
        PositionDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }


    //POST
    @PostMapping
    public ResponseEntity<PositionDTO> create(@RequestBody PositionDTO dto) {
        PositionDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }


    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<PositionDTO> update(@PathVariable Long id, @RequestBody PositionDTO request) {
        PositionDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
