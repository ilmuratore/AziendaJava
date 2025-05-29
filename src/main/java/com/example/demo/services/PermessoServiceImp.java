package com.example.demo.services;


import com.example.demo.dto.PermessoDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Permesso;
import com.example.demo.enums.TipoPermesso;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.interfaces.PermessoService;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PermessoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service

public class PermessoServiceImp implements PermessoService {

    private final PermessoRepository permessoRepository;
    private final AccountRepository accountRepository;

    public PermessoServiceImp(PermessoRepository permessoRepository, AccountRepository accountRepository) {
        this.permessoRepository = permessoRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public PermessoDTO crea(PermessoDTO dto) {
        Permesso entity = mapToEntity(dto);
        Permesso saved = permessoRepository.save(entity);
        if (dto.getAccountIds() != null) {
            dto.getAccountIds().forEach(accId -> {
                Account acc = accountRepository.findById(accId).orElseThrow(() -> new ResourceNotFoundException("Account non trovato id:" + accId));
                acc.setPermesso(saved);
                accountRepository.save(acc);
            });
        }
        return mapToDto(saved);
    }

    @Override
    public PermessoDTO aggiorna(Integer id, PermessoDTO dto) {
        Permesso exist = permessoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permesso non trovato id: " + id));
        exist.setTipoPermesso(TipoPermesso.valueOf(dto.getTipoPermesso()));
        Permesso aggiornato = permessoRepository.save(exist);
        return mapToDto(aggiornato);
    }

    ;

    @Override
    public void elimina(Integer id) {
        if (!permessoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permesso non trovato id" + id);
        }
        permessoRepository.deleteById(id);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public PermessoDTO trovaPerId(Integer id) {
        Permesso p = permessoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permesso non trovato id: " + id));
        return mapToDto(p);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public List<PermessoDTO> trovaTutti() {
        return permessoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    ;

    private Permesso mapToEntity(PermessoDTO dto) {
        Permesso p = new Permesso();
        p.setTipoPermesso(TipoPermesso.valueOf(String.valueOf(dto.getTipoPermesso())));
        return p;
    }

    ;

    private PermessoDTO mapToDto(Permesso p) {
        PermessoDTO dto = new PermessoDTO();
        dto.setId(p.getId());
        dto.setTipoPermesso(p.getTipoPermesso().name());
        dto.setAccountIds(p.getAccount().stream().map(Account::getId).collect(Collectors.toList()));
        return dto;
    }

    ;

};
