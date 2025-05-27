package com.example.demo.services;


import com.example.demo.dto.DipendenteDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Dipendente;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.DipendenteRepository;
import com.example.demo.repositories.PermessoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DipendenteServiceImp implements DipendenteService {

    private final DipendenteRepository dipendenteRepository;
    private final AccountRepository accountRepository;
    private final PermessoRepository permessoRepository;


    public DipendenteServiceImp(DipendenteRepository dipendenteRepository, AccountRepository accountRepository, PermessoRepository permessoRepository) {
        this.dipendenteRepository = dipendenteRepository;
        this.accountRepository = accountRepository;
        this.permessoRepository = permessoRepository;
    }

    ;

    @Override
    public DipendenteDTO crea(DipendenteDTO dto) {
        if (dto.getAccountId() != null && !accountRepository.existsById(dto.getAccountId())) {
            throw new ResourceNotFoundException("Account non trovato id:" + dto.getAccountId());
        }
        Dipendente entity = mapToEntity(dto);
        Dipendente saved = dipendenteRepository.save(entity);
        return mapToDto(saved);
    }

    ;


    @Override
    public DipendenteDTO aggiorna(Integer id, DipendenteDTO dto) {
        Dipendente esistente = dipendenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        applyDto(esistente, dto);
        return mapToDto(dipendenteRepository.save(esistente));
    }

    ;

    @Override
    public DipendenteDTO aggiornaPerCodiceFiscale(String codiceFiscale, DipendenteDTO dto) {
        Dipendente esistente = dipendenteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale));
        applyDto(esistente, dto);
        return mapToDto(dipendenteRepository.save(esistente));
    }

    ;


    @Override
    public void elimina(Integer id) {
        if (!dipendenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dipendente non trovato id: " + id);
        }
        dipendenteRepository.deleteById(id);
    }

    ;

    @Override
    public void eliminaPerCodiceFiscale(String codiceFiscale) {
        if (!dipendenteRepository.existsByCodiceFiscale(codiceFiscale)) {
            throw new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale);
        }
        dipendenteRepository.deleteByCodiceFiscale(codiceFiscale);
    }

    ;


    @Override
    @Transactional(readOnly = true)
    public DipendenteDTO trovaPerId(Integer id) {
        Dipendente d = dipendenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        return mapToDto(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public DipendenteDTO trovaPerCodiceFiscale(String codiceFiscale) {
        Dipendente d = dipendenteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale));
        return mapToDto(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public List<DipendenteDTO> trovaTutti() {
        return dipendenteRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    ;


    private void applyDto(Dipendente esistente, DipendenteDTO dto) {
        esistente.setNome(dto.getNome());
        esistente.setCognome(dto.getCognome());
        esistente.setCodiceFiscale(dto.getCodiceFiscale());
        esistente.setDataNascita(dto.getDataNascita());
        esistente.setDataAssunzione(dto.getDataAssunzione());
        esistente.setStipendio(dto.getStipendio());
        esistente.setAreaDiResp(dto.getAreaDiResp());
        esistente.setTipoManager(dto.getTipoManager());
        esistente.setRuolo(dto.getRuolo());
        if (dto.getAccountId() != null) {
            Account a = accountRepository.findById(dto.getAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account non trovato id: " + dto.getAccountId()));
            esistente.setAccount(a);
        }
    }

    ;


    private Dipendente mapToEntity(DipendenteDTO dto) {
        Dipendente d = new Dipendente();
        applyDto(d, dto);
        return d;
    }

    ;

    private DipendenteDTO mapToDto(Dipendente d) {
        DipendenteDTO dto = new DipendenteDTO();
        dto.setId(d.getId());
        dto.setNome(d.getNome());
        dto.setCognome(d.getCognome());
        dto.setCodiceFiscale(d.getCodiceFiscale());
        dto.setDataNascita(d.getDataNascita());
        dto.setDataAssunzione(d.getDataNascita());
        dto.setStipendio(d.getStipendio());
        dto.setAreaDiResp(d.getAreaDiResp());
        dto.setTipoManager(d.getTipoManager());
        dto.setRuolo(d.getRuolo());
        if (d.getAccount() != null) dto.setAccountId(d.getAccount().getId());
        return dto;
    }

    ;

}
