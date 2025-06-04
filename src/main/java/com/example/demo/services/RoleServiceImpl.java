package com.example.demo.services;


import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.RoleDTO;
import com.example.demo.entities.Permission;
import com.example.demo.entities.Role;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final EntityMapper mapper;

    @Override
    public RoleDTO create(RoleDTO dto){
        Set<Permission> permission = permissionRepository.findAllById(dto.getPermissionIds())
                .stream().collect(Collectors.toSet());
        Role newRole = mapper.toEntity(dto);
        newRole.setPermissions(permission);
        Role saved = roleRepository.save(newRole);
        return mapper.toDto(saved);
    }

    @Override
    public RoleDTO update(Long id, RoleDTO dto){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Role not found with id: " + id));
        if(dto.getName() != null){
            role.setName(dto.getName());
        }
        if(dto.getDescription() != null){
            role.setDescription(dto.getDescription());
        }
        if(dto.getPermissionIds() != null && !dto.getPermissionIds().isEmpty()){
            Set<Permission> permissions = dto.getPermissionIds().stream()
                    .map(permissionId -> permissionRepository.findById(permissionId)
                            .orElseThrow(()-> new EntityNotFoundException("Permission not found with id" + permissionId)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }
        Role updated = roleRepository.save(role);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO findById(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Role not found with id: " + id));
        return mapper.toDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> findAll(){
        return roleRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){
        if(!roleRepository.existsById(id)){
            throw  new EntityNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
}
