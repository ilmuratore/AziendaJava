package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.request.AccountPasswordResetDTO;
import com.example.demo.dto.request.CreateAccountRequestDTO;
import com.example.demo.dto.request.UpdateAccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.dto.response.AccountSummaryDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Persona;
import com.example.demo.entities.Role;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PersonaRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PersonaRepository personaRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;

    @Override
    public AccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        Persona persona = personaRepository.findById(request.getPersonaId()).orElseThrow(() -> new EntityNotFoundException("Persona con id " + request.getPersonaId() + " non trovata"));
        Set<Role> roles = request.getRoleIds().stream().map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role con id " + roleId + " non trovato"))).collect(Collectors.toSet());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account account = Account.builder().username(request.getUsername()).passwordHash(encodedPassword).persona(persona).roles(roles).emailVerified(false).enabled(true).failedAttempts(0).build();
        Account saved = accountRepository.save(account);
        return mapper.toAccountResponseDto(saved);
    }

    @Override
    public AccountResponseDTO updateAccount(Long id, UpdateAccountRequestDTO request) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            account.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            account.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getPersonaId() != null) {
            Persona persona = personaRepository.findById(request.getPersonaId()).orElseThrow(() -> new EntityNotFoundException("Persona con id " + request.getPersonaId() + " non trovata"));
            account.setPersona(persona);
        }
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = request.getRoleIds().stream().map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role con id " + roleId + " non trovato"))).collect(Collectors.toSet());
            account.setRoles(roles);
        }
        if (request.getEnabled() != null) {
            account.setEnabled(request.getEnabled());
        }
        if (request.getEmailVerified() != null) {
            account.setEmailVerified(request.getEmailVerified());
        }
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        return mapper.toAccountResponseDto(account);
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(mapper::toAccountResponseDto);
    }

    @Override
    public AccountResponseDTO lockAccount(Long id, AccountSummaryDTO lockInfo) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        Instant now = Instant.now();
        if (lockInfo.getLockUntil().isBefore(now)) {
            throw new IllegalArgumentException("lockUntil must be in the future");
        }
        account.setLockedUntil(lockInfo.getLockUntil());
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }


    @Override
    public AccountResponseDTO unlockAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setLockedUntil(null);
        account.setFailedAttempts(0);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    @Override
    public AccountResponseDTO resetPassword(AccountPasswordResetDTO resetInfo) {
        Account account = accountRepository.findAll().stream().filter(a -> a.getUsername().equals(resetInfo.getUsername())).findFirst().orElseThrow(() -> new EntityNotFoundException("Account with username" + resetInfo.getUsername() + "not found"));
        account.setPasswordHash(passwordEncoder.encode(resetInfo.getNewPassword()));
        account.setFailedAttempts(0);
        account.setLockedUntil(null);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    @Override
    public AccountResponseDTO verifyEmail(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setEmailVerified(true);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    @Override
    public AccountResponseDTO enableAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setEnabled(true);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    @Override
    public AccountResponseDTO disableAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setEnabled(false);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getAccountsByPersona(Long personaId, Pageable pageable) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + personaId));
        return (Page<AccountResponseDTO>) accountRepository.findAll(pageable).filter(account -> account.getPersona().getId().equals(persona.getId())).map(mapper::toAccountResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getAccountsByRole(Long roleId, Pageable pageable) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
        return (Page<AccountResponseDTO>) accountRepository.findAll(pageable).filter(account -> account.getRoles().contains(role)).map(mapper::toAccountResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getLockedAccounts(Pageable pageable) {
        Instant now = Instant.now();
        return (Page<AccountResponseDTO>) accountRepository.findAll(pageable).filter(account -> account.getLockedUntil() != null && account.getLockedUntil().isAfter(now)).map(mapper::toAccountResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountByUsername(String username) {
        Account account = accountRepository.findAll().stream().filter(a -> a.getUsername().equals(username)).findFirst().orElseThrow(() -> new EntityNotFoundException("Account con username " + username + " non trovato"));
        return mapper.toAccountResponseDto(account);
    }

}
