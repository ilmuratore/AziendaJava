package com.example.demo.services;

import com.example.demo.dto.AuditLogDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.AuditLog;
import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.services.interfaces.AuditLogService;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AccountRepository accountRepository;
    private final EntityMapper mapper;

    public AuditLogServiceImpl(
            AuditLogRepository auditLogRepository,
            AccountRepository accountRepository,
            EntityMapper mapper
    ) {
        this.auditLogRepository = auditLogRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }


    @Override
    public AuditLogDTO create(AuditLogDTO dto) {
        Account account = accountRepository.findById(dto.getChangedByAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + dto.getChangedByAccountId()));
        AuditLog newAuditLog = mapper.toEntity(dto);
        newAuditLog.setChangedBy(account);
        AuditLog saved = auditLogRepository.save(newAuditLog);
        return mapper.toDto(saved);
    }


    @Override
    public AuditLogDTO update(Long id, AuditLogDTO dto) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuditLog not found with id: " + id));
        if (dto.getEntityName() != null) {
            auditLog.setEntityName(dto.getEntityName());
        }
        if (dto.getEntityId() != null) {
            auditLog.setEntityId(dto.getEntityId());
        }
        if (dto.getAction() != null) {
            auditLog.setAction(dto.getAction());
        }
        if (dto.getChangedByAccountId() != null && !auditLog.getChangedBy().getId().equals(dto.getChangedByAccountId())) {
            Account account = accountRepository.findById(dto.getChangedByAccountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + dto.getChangedByAccountId()));
            auditLog.setChangedBy(account);
        }
        if (dto.getTimestamp() != null) {
            auditLog.setTimestamp(dto.getTimestamp());
        }
        if (dto.getDetails() != null) {
            auditLog.setDetails(dto.getDetails());
        }
        AuditLog updated = auditLogRepository.save(auditLog);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional(readOnly = true)
    public AuditLogDTO findById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuditLog not found with id: " + id));
        return mapper.toDto(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogDTO> findAll() {
        return auditLogRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!auditLogRepository.existsById(id)) {
            throw new EntityNotFoundException("AuditLog not found with id: " + id);
        }
        auditLogRepository.deleteById(id);
    }

}
