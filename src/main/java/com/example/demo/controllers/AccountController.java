package com.example.demo.controllers;


import com.example.demo.dto.AccountDTO;
import com.example.demo.services.AccountServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final AccountServiceImp service;

    public AccountController(AccountServiceImp service) {
        this.service = service;
    }

    ;

    //Get All
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> lista = service.trovaTutti();
        return ResponseEntity.ok(lista);
    }

    ;

    //Get id
    @GetMapping("/id/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Integer id) {
        AccountDTO dto = service.trovaPerId(id);
        return ResponseEntity.ok(dto);
    }

    ;

    // Create
    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        AccountDTO nuovo = service.crea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    ;

    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Integer id, @RequestBody AccountDTO updated) {
        AccountDTO aggiornato = service.aggiorna(id, updated);
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



