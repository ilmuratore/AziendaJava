package com.example.demo.controllers;

import com.example.demo.dto.request.PermissionRequestDTO;
import com.example.demo.dto.response.PermissionResponseDTO;
import com.example.demo.services.interfaces.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions", description = "Operazioni sui permessi | Permission management")
public class PermissionController {

    private final PermissionService service;

    @Operation(summary = "Crea un nuovo permesso")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Permesso creato con successo"), @ApiResponse(responseCode = "400", description = "Dati non validi"), @ApiResponse(responseCode = "409", description = "Permesso già esistente")})
    @PostMapping
    public ResponseEntity<PermissionResponseDTO> createPermission(@Valid @RequestBody PermissionRequestDTO requestDTO) {
        PermissionResponseDTO responseDTO = service.createPermission(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Recupera permesso per ID")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
        PermissionResponseDTO responseDTO = service.getPermissionById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Recupera permesso per nome")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<PermissionResponseDTO> getPermissionByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getPermissionByName(name));
    }

    @Operation(summary = "Recupera tutti i permessi con paginazione")
    @GetMapping
    public ResponseEntity<Page<PermissionResponseDTO>> getAllPermissions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PermissionResponseDTO> permissions = service.getAllPermissions(pageable);
        return ResponseEntity.ok(permissions);
    }

    @Operation(summary = "Aggiorna un permesso")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionRequestDTO requestDTO) {
        PermissionResponseDTO responseDTO = service.updatePermission(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Elimina un permesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        service.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verifica se un permesso esiste")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> permissionExists(@PathVariable Long id) {
        boolean exists = service.existsById(id);
        return ResponseEntity.ok(exists);
    }

   
    @Operation(summary = "Recupera permessi di un ruolo specifico")
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<Set<PermissionResponseDTO>> getPermissionsByRole(@PathVariable Long roleId) {
        Set<PermissionResponseDTO> permissions = service.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

    @Operation(summary = "Recupera permessi non assegnati")
    @GetMapping("/unassigned")
    public ResponseEntity<Page<PermissionResponseDTO>> getUnassignedPermissions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PermissionResponseDTO> permissions = service.getUnassignedPermissions(pageable);
        return ResponseEntity.ok(permissions);
    }
}
