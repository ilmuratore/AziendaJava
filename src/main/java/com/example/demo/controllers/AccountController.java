package com.example.demo.controllers;

import com.example.demo.dto.AccountDTO;
import com.example.demo.services.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountServiceImpl service;

    public AccountController(AccountServiceImpl service) {
        this.service = service;
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //Get one by id
    @GetMapping("/id/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        AccountDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }


    //POST
    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        AccountDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }


    //Update
    @PutMapping("/update/id/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody AccountDTO request){
        AccountDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //Delete
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
