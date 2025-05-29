package com.example.demo.controllers;

import com.example.demo.dto.PermessoDTO;
import com.example.demo.services.PermessoServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permesso")
public class PermessoController {

    private final PermessoServiceImp service;

    public PermessoController(PermessoServiceImp service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<PermessoDTO>> getAll() {
        List<PermessoDTO> lista = service.trovaTutti();
        return ResponseEntity.ok(lista);
    }

    ;

    //Get id
    @GetMapping("/id/{id}")
    public ResponseEntity<PermessoDTO> getById(@PathVariable Integer id) {
        PermessoDTO dto = service.trovaPerId(id);
        return ResponseEntity.ok(dto);
    }

    ;

    //Create
    @PostMapping
    public ResponseEntity<PermessoDTO> create(@RequestBody PermessoDTO dto) {
        PermessoDTO nuovo = service.crea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    ;

    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<PermessoDTO> update(@PathVariable Integer id, @RequestBody PermessoDTO updated) {
        PermessoDTO aggiornato = service.aggiorna(id, updated);
        return ResponseEntity.ok(aggiornato);
    }

    ;

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.elimina(id);
        return ResponseEntity.noContent().build();
    }

    ;

}


