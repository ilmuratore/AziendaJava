package com.example.demo.interfaces;

import com.example.demo.dto.PersonaDTO;

import java.util.List;

public interface PersonaService {
    PersonaDTO create(PersonaDTO dto);
    PersonaDTO update(Long id, PersonaDTO dto);
    PersonaDTO findById(Long id);
    List<PersonaDTO> findAll();
    void delete(Long id);
}
