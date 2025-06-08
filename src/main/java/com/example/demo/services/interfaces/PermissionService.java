package com.example.demo.services.interfaces;


import com.example.demo.dto.request.PermissionRequestDTO;
import com.example.demo.dto.response.PermissionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;


public interface PermissionService {

    PermissionResponseDTO createPermission(PermissionRequestDTO requestDTO);
    PermissionResponseDTO getPermissionById(Long id);
    PermissionResponseDTO getPermissionByName(String name);
    Page<PermissionResponseDTO> getAllPermissions(Pageable pageable);
    PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO requestDTO);
    void deletePermission(Long id);
    boolean existsByName(String name);
    boolean existsById(Long id);
    // Metodi per gestire le relazioni con i ruoli
    Set<PermissionResponseDTO> getPermissionsByRoleId(Long roleId);
    Page<PermissionResponseDTO> getUnassignedPermissions(Pageable pageable);

}
