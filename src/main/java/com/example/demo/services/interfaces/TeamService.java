package com.example.demo.services.interfaces;

import com.example.demo.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    TeamDTO create(TeamDTO dto);
    TeamDTO update(Long id, TeamDTO dto);
    TeamDTO findById(Long id);
    List<TeamDTO> findAll();
    void delete(Long id);
}
