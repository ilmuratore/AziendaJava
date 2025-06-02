package com.example.demo.interfaces;

import com.example.demo.dto.AuditLogDTO;

import java.util.List;

public interface AuditLogService {
    AuditLogDTO create(AuditLogDTO dto);
    AuditLogDTO update(Long id, AuditLogDTO dto);
    AuditLogDTO findById(Long id);
    List<AuditLogDTO> findAll();
    void delete(Long id);
}
