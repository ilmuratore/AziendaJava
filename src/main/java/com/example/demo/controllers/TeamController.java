package com.example.demo.controllers;

import com.example.demo.dto.TeamDTO;
import com.example.demo.services.interfaces.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        List<TeamDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get one by id
    @GetMapping("/id/{id}")
    public ResponseEntity<TeamDTO> getById(@PathVariable Long id) {
        TeamDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }


    //POST
    @PostMapping
    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO dto) {
        TeamDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }


    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<TeamDTO> update(@PathVariable Long id, @RequestBody TeamDTO request) {
        TeamDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
