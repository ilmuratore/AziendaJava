package com.example.demo.interfaces;

import com.example.demo.dto.PositionDTO;

import java.util.List;

public interface PositionService {
    PositionDTO create(PositionDTO dto);
    PositionDTO update(Long id, PositionDTO dto);
    PositionDTO findById(Long id);
    List<PositionDTO> findAll();
    void delete(Long id);
}
