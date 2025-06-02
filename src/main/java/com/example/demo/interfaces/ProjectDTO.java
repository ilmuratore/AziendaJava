package com.example.demo.interfaces;

import com.example.demo.dto.AccountDTO;

import java.util.List;

public interface ProjectDTO {
   ProjectDTO create(AccountDTO dto);
   ProjectDTO update(Long id,ProjectDTO dto);
   ProjectDTO findById(Long id);
    List<AccountDTO> findAll();
    void delete(Long id);
}
