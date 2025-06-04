package com.example.demo.services.interfaces;

import com.example.demo.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    PermissionDTO create(PermissionDTO dto);
    PermissionDTO update(Long id, PermissionDTO dto);
    PermissionDTO findById(Long id);
    List<PermissionDTO> findAll();
    void delete(Long id);
}
