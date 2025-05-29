package com.example.demo.controllers;


import com.example.demo.dto.PersonaDTO;
import com.example.demo.services.PersonaServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    private final PersonaServiceImp service;


    public PersonaController(PersonaServiceImp service) {
        this.service = service;
    }

    //Get all light
    @GetMapping
    public ResponseEntity<List<PersonaDTO>> getAll() {
        List<PersonaDTO> lista = service.trovaTutti();
        return ResponseEntity.ok(lista);
    }

    ;

    //Get by id, codice fiscale light
    @GetMapping("/id/{id}")
    public ResponseEntity<PersonaDTO> getById(@PathVariable Integer id) {
        PersonaDTO dto = service.trovaPerId(id);
        return ResponseEntity.ok(dto);
    }

    ;

    @GetMapping("/cf/{codiceFiscale}")
    public ResponseEntity<PersonaDTO> getByCodiceFiscale(@PathVariable String codiceFiscale) {
        PersonaDTO dto = service.trovaPerCodiceFiscale(codiceFiscale);
        return ResponseEntity.ok(dto);
    }

    ;


    //Create Light
    @PostMapping
    public ResponseEntity<PersonaDTO> create(@RequestBody PersonaDTO dto) {
        PersonaDTO nuovo = service.crea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    ;


    //Update per id e cf
    @PutMapping("/update/id/{id}")
    public ResponseEntity<PersonaDTO> update(@PathVariable Integer id, @RequestBody PersonaDTO updated) {
        PersonaDTO aggiornato = service.aggiorna(id, updated);
        return ResponseEntity.ok(aggiornato);
    }

    ;

    @PutMapping("/update/cf/{codiceFiscale}")
    public ResponseEntity<PersonaDTO> updateByCf(@PathVariable String codiceFiscale, @RequestBody PersonaDTO updated) {
        PersonaDTO aggiornato = service.aggiornaPerCodiceFiscale(codiceFiscale, updated);
        return ResponseEntity.ok(aggiornato);
    }

    ;

    //Delete per id e cf
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.elimina(id);
        return ResponseEntity.noContent().build();
    }

    ;

    @DeleteMapping("/delete/cf/{codiceFiscale}")
    public ResponseEntity<Void> deleteByCf(@PathVariable String codiceFiscale) {
        service.eliminaPerCodiceFiscale(codiceFiscale);
        return ResponseEntity.noContent().build();
    }

    ;
}
