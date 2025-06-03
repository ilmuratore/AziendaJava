package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Position;
import com.example.demo.repositories.PositionRepository;
import com.example.demo.services.interfaces.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final EntityMapper mapper;

    @Override
    public PositionDTO create(PositionDTO dto) {
        Position newPosition = mapper.toEntity(dto);
        Position saved = positionRepository.save(newPosition);
        return mapper.toDto(saved);
    }

    @Override
    public PositionDTO update(Long id, PositionDTO dto){
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));
        if(dto.getTitle() != null){
            position.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null){
            position.setTitle(dto.getTitle());
        }
        if(dto.getSalaryGrade() != null){
            position.setSalaryGrade(dto.getSalaryGrade());
        }
        Position updated = positionRepository.save(position);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PositionDTO findById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));
        return mapper.toDto(position);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionDTO> findAll() {
        return positionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!positionRepository.existsById(id)) {
            throw new EntityNotFoundException("Position not found with id: " + id);
        }
        positionRepository.deleteById(id);
    }
}
