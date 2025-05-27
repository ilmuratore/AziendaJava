package com.example.demo.controllers;


import com.example.demo.dto.DipendenteDTOFull;
import com.example.demo.dto.DipendenteDTOLight;
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
    public ResponseEntity<List<DipendenteDTOLight>> getAll() {
        List<DipendenteDTOLight> lista = service.trovaTutti();
        return ResponseEntity.ok(lista);
    }

    ;
/*
    //Get all Full
    @GetMapping("/full")
    public ResponseEntity<List<DipendenteDTOFull>> getAllFull(){
        List<DipendenteDTOFull> lista = service.trovaTuttiFull();
        return ResponseEntity.ok(lista);
    }
*/
    //Get by id, codice fiscale light
    @GetMapping("/id/{id}")
    public ResponseEntity<DipendenteDTOLight> getById(@PathVariable Integer id) {
        DipendenteDTOLight dto = service.trovaPerId(id);
        return ResponseEntity.ok(dto);
    }

    ;

    @GetMapping("/cf/{codiceFiscale}")
    public ResponseEntity<DipendenteDTOLight> getByCodiceFiscale(@PathVariable String codiceFiscale) {
        DipendenteDTOLight dto = service.trovaPerCodiceFiscale(codiceFiscale);
        return ResponseEntity.ok(dto);
    }

    ;
/*
    //Get by id, codice fiscale Full
    @GetMapping("/full/id/{id}")
    public ResponseEntity<DipendenteDTOFull> getByIdFull(@PathVariable Integer id){
        DipendenteDTOFull dto = service.trovaPerIdFull(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/full/cf/{codiceFiscale}")
    public ResponseEntity<DipendenteDTOFull> getByCodiceFiscaleFull(@PathVariable String codiceFiscale){
        DipendenteDTOFull dto = service.trovaPerCodiceFiscaleFull(codiceFiscale);
        return ResponseEntity.ok(dto);
    }
*/

    //Create Light
    @PostMapping
    public ResponseEntity<DipendenteDTOLight> create(@RequestBody DipendenteDTOLight dto) {
        DipendenteDTOLight nuovo = service.crea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    ;

    @PostMapping("/full")
    public ResponseEntity<Dipendente> creaJson(@RequestBody String body){
        System.out.println("Ciao");
        Dipendente nuovo = service.insertAccountPermesso(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }
 /*
    //Create Full
    @PostMapping("full")
    public ResponseEntity<DipendenteDTOFull> createFull(@RequestBody DipendenteDTOFull dto){
        DipendenteDTOFull nuovo = service.creaFull(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }
*/
    //Update per id e cf
    @PutMapping("/update/id/{id}")
    public ResponseEntity<DipendenteDTOLight> update(@PathVariable Integer id, @RequestBody DipendenteDTOLight updated) {
        DipendenteDTOLight aggiornato = service.aggiorna(id, updated);
        return ResponseEntity.ok(aggiornato);
    }

    ;

    @PutMapping("/update/cf/{codiceFiscale}")
    public ResponseEntity<DipendenteDTOLight> updateByCf(@PathVariable String codiceFiscale, @RequestBody DipendenteDTOLight updated) {
        DipendenteDTOLight aggiornato = service.aggiornaPerCodiceFiscale(codiceFiscale, updated);
        return ResponseEntity.ok(aggiornato);
    }

    ;

/*
    //Update per id e cf FULL
    @PutMapping("full/update/id/{id}")
    public ResponseEntity<DipendenteDTOFull> updateFull(@PathVariable Integer id, @RequestBody DipendenteDTOFull updated){
        DipendenteDTOFull aggiornato = service.aggiornaFull(id, updated);
        return ResponseEntity.ok(aggiornato);
    }

    @PutMapping("full/update/cf/{codiceFiscale}")
    public ResponseEntity<DipendenteDTOFull> updateFullCf(@PathVariable String codiceFiscale, @RequestBody DipendenteDTOFull updated){
        DipendenteDTOFull aggiornato = service.aggiornaCfFull(codiceFiscale, updated);
        return ResponseEntity.ok(aggiornato);
    }
*/
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
