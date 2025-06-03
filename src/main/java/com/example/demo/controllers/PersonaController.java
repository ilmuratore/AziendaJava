package com.example.demo.controllers;

import com.example.demo.dto.PersonaDTO;
import com.example.demo.services.interfaces.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Persona")
public class PersonaController {

    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<PersonaDTO>> getAll() {
        List<PersonaDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get one by id
    @GetMapping("/id/{id}")
    public ResponseEntity<PersonaDTO> getById(@PathVariable Long id) {
        PersonaDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }


    //POST
    @PostMapping
    public ResponseEntity<PersonaDTO> create(@RequestBody PersonaDTO dto) {
        PersonaDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }


    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<PersonaDTO> update(@PathVariable Long id, @RequestBody PersonaDTO request) {
        PersonaDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
