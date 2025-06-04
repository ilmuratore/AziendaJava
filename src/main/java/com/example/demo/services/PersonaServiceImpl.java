package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.PersonaDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import com.example.demo.services.interfaces.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final EntityMapper mapper;

    @Override
    public PersonaDTO create(PersonaDTO dto) {
        Persona newPersona = mapper.toEntity(dto);

        if (dto.getAccountId() != null) {
            Account account = accountRepository.findById(dto.getAccountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + dto.getAccountId()));
            newPersona.setAccount(account);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            newPersona.setDepartment(department);
        }

        if (dto.getPositionId() != null) {
            Position position = positionRepository.findById(dto.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + dto.getPositionId()));
            newPersona.setPosition(position);
        }

        if (dto.getTeamIds() != null && !dto.getTeamIds().isEmpty()) {
            Set<Team> teams = teamRepository.findAllById(dto.getTeamIds())
                    .stream().collect(Collectors.toSet());
            if (teams.size() != dto.getTeamIds().size()) {
                throw new EntityNotFoundException("Some teams not found");
            }
            newPersona.setTeams(teams);
        }

        if (dto.getProjectIds() != null && !dto.getProjectIds().isEmpty()) {
            Set<Project> projects = projectRepository.findAllById(dto.getProjectIds())
                    .stream().collect(Collectors.toSet());
            if (projects.size() != dto.getProjectIds().size()) {
                throw new EntityNotFoundException("Some projects not found");
            }
            newPersona.setProjects(projects);
        }

        Persona saved = personaRepository.save(newPersona);
        return mapper.toDto(saved);
    }

    @Override
    public PersonaDTO update(Long id, PersonaDTO dto) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + id));

        if (dto.getFirstName() != null) {
            persona.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            persona.setLastName(dto.getLastName());
        }
        if (dto.getBirthDate() != null) {
            persona.setBirthDate(dto.getBirthDate());
        }
        if (dto.getGender() != null) {
            persona.setGender(dto.getGender());
        }
        if (dto.getTaxCode() != null) {
            persona.setTaxCode(dto.getTaxCode());
        }
        if (dto.getAddress() != null) {
            persona.setAddress(dto.getAddress());
        }
        if (dto.getCity() != null) {
            persona.setCity(dto.getCity());
        }
        if (dto.getPostalCode() != null) {
            persona.setPostalCode(dto.getPostalCode());
        }
        if (dto.getCountry() != null) {
            persona.setCountry(dto.getCountry());
        }
        if (dto.getPhoneNumber() != null) {
            persona.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getEmail() != null) {
            persona.setEmail(dto.getEmail());
        }
        if (dto.getHireDate() != null) {
            persona.setHireDate(dto.getHireDate());
        }
        if (dto.getTerminationDate() != null) {
            persona.setTerminationDate(dto.getTerminationDate());
        }

        if (dto.getAccountId() != null && (persona.getAccount() == null || !persona.getAccount().getId().equals(dto.getAccountId()))) {
            Account account = accountRepository.findById(dto.getAccountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + dto.getAccountId()));
            persona.setAccount(account);
        }

        if (dto.getDepartmentId() != null && (persona.getDepartment() == null || !persona.getDepartment().getId().equals(dto.getDepartmentId()))) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            persona.setDepartment(department);
        }

        if (dto.getPositionId() != null && (persona.getPosition() == null || !persona.getPosition().getId().equals(dto.getPositionId()))) {
            Position position = positionRepository.findById(dto.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + dto.getPositionId()));
            persona.setPosition(position);
        }

        if (dto.getTeamIds() != null) {
            if (dto.getTeamIds().isEmpty()) {
                persona.setTeams(new HashSet<>());
            } else {
                Set<Team> teams = new HashSet<>();
                for (Long teamId : dto.getTeamIds()) {
                    Team team = teamRepository.findById(teamId)
                            .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));
                    teams.add(team);
                }
                persona.setTeams(teams);
            }
        }

        if (dto.getProjectIds() != null) {
            if (dto.getProjectIds().isEmpty()) {
                persona.setProjects(new HashSet<>());
            } else {
                Set<Project> projects = new HashSet<>();
                for (Long projectId : dto.getProjectIds()) {
                    Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
                    projects.add(project);
                }
                persona.setProjects(projects);
            }
        }

        Persona updated = personaRepository.save(persona);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaDTO findById(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + id));
        return mapper.toDto(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> findAll() {
        return personaRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new EntityNotFoundException("Persona not found with id: " + id);
        }
        personaRepository.deleteById(id);
    }
}