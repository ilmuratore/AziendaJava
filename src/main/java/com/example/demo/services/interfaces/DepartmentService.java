package com.example.demo.services.interfaces;



import com.example.demo.dto.request.CreateDepartmentRequestDTO;
import com.example.demo.dto.request.UpdateDepartmentRequestDTO;
import com.example.demo.dto.response.DepartmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DepartmentService {

    DepartmentResponseDTO createDepartment(CreateDepartmentRequestDTO request);
    DepartmentResponseDTO updateDepartment(Long id, UpdateDepartmentRequestDTO request);
    DepartmentResponseDTO getDepartmentById(Long id);
    void deleteDepartment(Long id);
    Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable);

}
