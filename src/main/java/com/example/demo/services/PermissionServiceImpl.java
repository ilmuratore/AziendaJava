package com.example.demo.services;


import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.request.PermissionRequestDTO;
import com.example.demo.dto.response.PermissionResponseDTO;
import com.example.demo.entities.Permission;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.services.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final EntityMapper mapper;


    @Override
    public PermissionResponseDTO createPermission(PermissionRequestDTO requestDTO) {
        Permission permission = Permission.builder().name(requestDTO.getName()).description(requestDTO.getDescription()).build();
        Permission saved = permissionRepository.save(permission);
        return mapper.toPermissionResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponseDTO getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        return mapper.toPermissionResponseDto(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponseDTO getPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Permission not found with name: " + name));
        return mapper.toPermissionResponseDto(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionResponseDTO> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(mapper::toPermissionResponseDto);
    }

    @Override
    public PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO requestDTO) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        if (requestDTO.getName() != null && !requestDTO.getName().isBlank()) {
            permission.setName(requestDTO.getName());
        }
        if (requestDTO.getDescription() != null) {
            permission.setDescription(requestDTO.getDescription());
        }
        Permission updated = permissionRepository.save(permission);
        return mapper.toPermissionResponseDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return permissionRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return permissionRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PermissionResponseDTO> getPermissionsByRoleId(Long roleId) {
        return permissionRepository.findByRoleId(roleId).stream().map(mapper::toPermissionResponseDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionResponseDTO> getUnassignedPermissions(Pageable pageable) {
        return permissionRepository.findUnassignedPermissions(pageable).map(mapper::toPermissionResponseDto);
    }

    @Override
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new EntityNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

}
