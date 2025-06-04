package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.TeamDTO;
import com.example.demo.entities.Team;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.services.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final EntityMapper mapper;

    @Override
    public TeamDTO create(TeamDTO dto){
        Team newTeam = mapper.toEntity(dto);
        Team saved = teamRepository.save(newTeam);
        return mapper.toDto(saved);
    }

    @Override
    public TeamDTO update(Long id, TeamDTO dto){
        Team team = teamRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Team not found with id: " + id));
        if(dto.getName() != null){
            team.setName(dto.getName());
        }
        if(dto.getDescription() != null){
            team.setDescription(dto.getDescription());
        }
        Team updated = teamRepository.save(team);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id){
        Team team = teamRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Team not found with id: " + id));
        return mapper.toDto(team);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamDTO> findAll(){
        return teamRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){
        if(!teamRepository.existsById(id)){
            throw  new EntityNotFoundException("Team not found with id: " + id);
        } teamRepository.deleteById(id);
    }

}
