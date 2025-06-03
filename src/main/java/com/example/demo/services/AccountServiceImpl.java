package com.example.demo.services;

import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.AccountDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Persona;
import com.example.demo.entities.Role;
import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.services.interfaces.AccountService;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PersonaRepository;
import com.example.demo.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PersonaRepository personaRepository;
    private final RoleRepository roleRepository;
    private final EntityMapper mapper;


    @Override
    public AccountDTO create(AccountDTO dto) {
        Persona persona = personaRepository.findById(dto.getPersonaId())
                .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + dto.getPersonaId()));
        Set<Role> roles = roleRepository.findAllById(dto.getRoleIds())
                .stream().collect(Collectors.toSet());
        Account newAccount = mapper.toEntity(dto);
        newAccount.setPersona(persona);
        newAccount.setRoles(roles);
        Account saved = accountRepository.save(newAccount);
        return mapper.toDto(saved);
    }

    @Override
    public AccountDTO update(Long id, AccountDTO dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        if (dto.getUsername() != null) {
            account.setUsername(dto.getUsername());
        }
        if (dto.getPasswordHash() != null) {
            account.setPasswordHash(dto.getPasswordHash());
        }
        if (dto.getEmailVerified() != null) {
            account.setEmailVerified(dto.getEmailVerified());
        }
        if (dto.getEnabled() != null) {
            account.setEnabled(dto.getEnabled());
        }
        if (dto.getLastLogin() != null) {
            account.setLastLogin(dto.getLastLogin());
        }
        if (dto.getFailedAttempts() != null) {
            account.setFailedAttempts(dto.getFailedAttempts());
        }
        if (dto.getLockedUntil() != null) {
            account.setLockedUntil(dto.getLockedUntil());
        }
        if (dto.getCreatedAt() != null) {
            account.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            account.setUpdatedAt(dto.getUpdatedAt());
        }
        if (dto.getPersonaId() != null && !account.getPersona().getId().equals(dto.getPersonaId())) {
            Persona persona = personaRepository.findById(dto.getPersonaId())
                    .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + dto.getPersonaId()));
            account.setPersona(persona);
        }
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = dto.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId)))
                    .collect(Collectors.toSet());
            account.setRoles(roles);
        }
        Account updated = accountRepository.save(account);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        return mapper.toDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
