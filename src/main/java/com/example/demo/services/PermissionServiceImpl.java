package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.PermissionDTO;
import com.example.demo.entities.Permission;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.services.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final EntityMapper mapper;



    @Override
    public PermissionDTO create(PermissionDTO dto){
        Permission newPermission = mapper.toEntity(dto);
        Permission saved = permissionRepository.save(newPermission);
        return mapper.toDto(saved);
    }

    @Override
    public PermissionDTO update(Long id, PermissionDTO dto){
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Permission not found with id: " + id));
        if(dto.getName() != null){
            permission.setName(dto.getName());
        }
        if(dto.getDescription() != null){
            permission.setDescription(dto.getDescription());
        }
        Permission updated = permissionRepository.save(permission);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDTO findById(Long id){
        Permission permission = permissionRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Permission not found with id: " + id));
        return mapper.toDto(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> findAll(){
        return permissionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){
        if(!permissionRepository.existsById(id)){
            throw  new EntityNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }
}
