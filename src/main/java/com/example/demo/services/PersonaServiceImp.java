package com.example.demo.services;


import com.example.demo.dto.PersonaDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Persona;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.interfaces.PersonaService;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PersonaRepository;
import com.example.demo.repositories.PermessoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonaServiceImp implements PersonaService {

    private final PersonaRepository personaRepository;
    private final AccountRepository accountRepository;
    private final PermessoRepository permessoRepository;


    public PersonaServiceImp(PersonaRepository personaRepository, AccountRepository accountRepository, PermessoRepository permessoRepository) {
        this.personaRepository = personaRepository;
        this.accountRepository = accountRepository;
        this.permessoRepository = permessoRepository;
    }

    ;

    @Override
    public PersonaDTO crea(PersonaDTO dto) {
        if (dto.getAccountId() != null && !accountRepository.existsById(dto.getAccountId())) {
            throw new ResourceNotFoundException("Account non trovato id:" + dto.getAccountId());
        }
        Persona entity = mapToEntity(dto);
        Persona saved = personaRepository.save(entity);
        return mapToDto(saved);
    }

    ;


    @Override
    public PersonaDTO aggiorna(Integer id, PersonaDTO dto) {
        Persona esistente = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        applyDto(esistente, dto);
        return mapToDto(personaRepository.save(esistente));
    }

    ;

    @Override
    public PersonaDTO aggiornaPerCodiceFiscale(String codiceFiscale, PersonaDTO dto) {
        Persona esistente = personaRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale));
        applyDto(esistente, dto);
        return mapToDto(personaRepository.save(esistente));
    }

    ;


    @Override
    public void elimina(Integer id) {
        if (!personaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dipendente non trovato id: " + id);
        }
        personaRepository.deleteById(id);
    }

    ;

    @Override
    public void eliminaPerCodiceFiscale(String codiceFiscale) {
        if (!personaRepository.existsByCodiceFiscale(codiceFiscale)) {
            throw new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale);
        }
        personaRepository.deleteByCodiceFiscale(codiceFiscale);
    }

    ;


    @Override
    @Transactional(readOnly = true)
    public PersonaDTO trovaPerId(Integer id) {
        Persona d = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        return mapToDto(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public PersonaDTO trovaPerCodiceFiscale(String codiceFiscale) {
        Persona d = personaRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale));
        return mapToDto(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> trovaTutti() {
        return personaRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    ;


    private void applyDto(Persona esistente, PersonaDTO dto) {
        esistente.setNome(dto.getNome());
        esistente.setCognome(dto.getCognome());
        esistente.setCodiceFiscale(dto.getCodiceFiscale());
        esistente.setDataNascita(dto.getDataNascita());
        esistente.setCittaNascita(dto.getCittaNascita());
        esistente.setIndirizzoResidenza(dto.getIndirizzoResidenza());
        esistente.setCittaResidenza(dto.getCittaResidenza());
        esistente.setPaeseResidenza(dto.getPaeseResidenza());
        esistente.setCapResidenza(dto.getCapResidenza());
        if (dto.getAccountId() != null) {
            Account a = accountRepository.findById(dto.getAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account non trovato id: " + dto.getAccountId()));
            esistente.setAccount(a);
        }
    }

    ;


    private Persona mapToEntity(PersonaDTO dto) {
        Persona d = new Persona();
        applyDto(d, dto);
        return d;
    }

    ;

    private PersonaDTO mapToDto(Persona d) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(d.getId());
        dto.setNome(d.getNome());
        dto.setCognome(d.getCognome());
        dto.setCodiceFiscale(d.getCodiceFiscale());
        dto.setDataNascita(d.getDataNascita());
        dto.setCittaNascita(d.getCittaNascita());
        dto.setIndirizzoResidenza(d.getIndirizzoResidenza());
        dto.setCittaResidenza(d.getCittaResidenza());
        dto.setPaeseResidenza(d.getPaeseResidenza());
        dto.setCapResidenza(d.getCapResidenza());
        if (d.getAccount() != null) dto.setAccountId(d.getAccount().getId());
        return dto;
    }

    ;

}
