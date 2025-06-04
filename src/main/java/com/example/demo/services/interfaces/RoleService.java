package com.example.demo.services.interfaces;

import com.example.demo.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO create(RoleDTO dto);
    RoleDTO update(Long id, RoleDTO dto);
    RoleDTO findById(Long id);
    List<RoleDTO> findAll();
    void delete(Long id);

}
