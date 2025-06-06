package com.example.demo.services.interfaces;



import com.example.demo.dto.request.CreateDepartmentRequestDTO;
import com.example.demo.dto.request.UpdateDepartmentRequestDTO;
import com.example.demo.dto.response.DepartmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    /**
     * Crea un nuovo dipartimento.
     *
     * @param request DTO con i dati per la creazione.
     * @return il DepartmentResponseDTO con i dati appena salvati.
     */
    DepartmentResponseDTO createDepartment(CreateDepartmentRequestDTO request);
    /**
     * Aggiorna un dipartimento esistente.
     *
     * @param id      ID del dipartimento da aggiornare.
     * @param request DTO con i campi da modificare.
     * @return DepartmentResponseDTO con i dati aggiornati.
     */
    DepartmentResponseDTO updateDepartment(Long id, UpdateDepartmentRequestDTO request);
    /**
     * Recupera un dipartimento tramite ID.
     *
     * @param id ID del dipartimento.
     * @return DepartmentResponseDTO corrispondente.
     */
    DepartmentResponseDTO getDepartmentById(Long id);
    /**
     * Elimina un dipartimento tramite ID.
     *
     * @param id ID del dipartimento da eliminare.
     */
    void deleteDepartment(Long id);
    /**
     * Recupera tutti i dipartimenti (paginated).
     *
     * @param pageable paginazione e ordinamento.
     * @return pagina di DepartmentResponseDTO.
     */
    Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable);




}
