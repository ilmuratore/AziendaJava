package com.example.demo.controllers;


import com.example.demo.dto.DipendenteDTO;
import com.example.demo.entities.Dipendente;
import com.example.demo.services.DipendenteServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dipendenti")
public class DipendenteController {

    private final DipendenteServiceImp service;


    public DipendenteController(DipendenteServiceImp service) {
        this.service = service;
    }

    //Get all light
    @GetMapping
    public ResponseEntity<List<DipendenteDTO>> getAll() {
        List<DipendenteDTO> lista = service.trovaTutti();
        return ResponseEntity.ok(lista);
    }

    ;

    //Get by id, codice fiscale light
    @GetMapping("/id/{id}")
    public ResponseEntity<DipendenteDTO> getById(@PathVariable Integer id) {
        DipendenteDTO dto = service.trovaPerId(id);
        return ResponseEntity.ok(dto);
    }

    ;

    @GetMapping("/cf/{codiceFiscale}")
    public ResponseEntity<DipendenteDTO> getByCodiceFiscale(@PathVariable String codiceFiscale) {
        DipendenteDTO dto = service.trovaPerCodiceFiscale(codiceFiscale);
        return ResponseEntity.ok(dto);
    }

    ;


    //Create Light
    @PostMapping
    public ResponseEntity<DipendenteDTO> create(@RequestBody DipendenteDTO dto) {
        DipendenteDTO nuovo = service.crea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    ;


    //Update per id e cf
    @PutMapping("/update/id/{id}")
    public ResponseEntity<DipendenteDTO> update(@PathVariable Integer id, @RequestBody DipendenteDTO updated) {
        DipendenteDTO aggiornato = service.aggiorna(id, updated);
        return ResponseEntity.ok(aggiornato);
    }

    ;

    @PutMapping("/update/cf/{codiceFiscale}")
    public ResponseEntity<DipendenteDTO> updateByCf(@PathVariable String codiceFiscale, @RequestBody DipendenteDTO updated) {
        DipendenteDTO aggiornato = service.aggiornaPerCodiceFiscale(codiceFiscale, updated);
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
