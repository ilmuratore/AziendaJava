package com.example.demo.controllers;

import com.example.demo.dto.request.CreateDepartmentRequestDTO;
import com.example.demo.dto.request.UpdateDepartmentRequestDTO;
import com.example.demo.dto.response.DepartmentResponseDTO;
import com.example.demo.services.interfaces.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Departments", description = "Controller per la gestione dei dipartimenti")
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    //GET ALL
    @Operation(summary = "Elenco dipartimenti (paginated)", description = "Restituisce una pagina di dipartimenti. Parametri di paginazione e sorting tramite query string (es. ?page=0&size=10&sort=name,asc)")
    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(Pageable pageable) {
        Page<DepartmentResponseDTO> page = service.getAllDepartments(pageable);
        return ResponseEntity.ok(page);
    }

    //GET ID
    @Operation(summary = "Recupera un dipartimento per ID", description = "Restituisce i dati del dipartimento specificato da ID")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDTO dto = service.getDepartmentById(id);
        return ResponseEntity.ok(dto);
    }

    //POST
    @Operation(summary = "Crea un nuovo dipartimento", description = "Crea un dipartimento e restituisce i dati salvati")
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody CreateDepartmentRequestDTO request) {
        DepartmentResponseDTO created = service.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //PUT
    @Operation(summary = "Aggiorna un dipartimento esistente", description = "Aggiorna i campi forniti del dipartimento specificato da ID")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody UpdateDepartmentRequestDTO request) {
        DepartmentResponseDTO updated = service.updateDepartment(id, request);
        return ResponseEntity.ok(updated);
    }

    //DELETE
    @Operation(summary = "Elimina un dipartimento per ID", description = "Elimina il dipartimento specificato. Se non esiste, restituisce 404.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        service.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }


}
