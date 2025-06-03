package com.example.demo.controllers;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.services.interfaces.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll(){
        List<DepartmentDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    //GET ID
    @GetMapping("/id/{id}")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable Long id){
        DepartmentDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    //POST
    @PostMapping
    public ResponseEntity<DepartmentDTO> create(@RequestBody DepartmentDTO dto){
        DepartmentDTO entity = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    //PUT
    @PutMapping("/update/id/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @RequestBody DepartmentDTO request){
        DepartmentDTO entity = service.update(id, request);
        return ResponseEntity.ok(entity);
    }

    //DELETE
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
