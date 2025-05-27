package com.example.demo.services;


import com.example.demo.dto.DipendenteDTOFull;
import com.example.demo.dto.DipendenteDTOLight;
import com.example.demo.entities.Account;
import com.example.demo.entities.Dipendente;
import com.example.demo.entities.Permesso;
import com.example.demo.entities.TipoPermesso;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.DipendenteRepository;
import com.example.demo.repositories.PermessoRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public DipendenteDTOLight crea(DipendenteDTOLight dto) {
        if (dto.getAccountId() != null && !accountRepository.existsById(dto.getAccountId())) {
            throw new ResourceNotFoundException("Account non trovato id:" + dto.getAccountId());
        }
        Dipendente entity = mapToEntity(dto);
        Dipendente saved = dipendenteRepository.save(entity);
        return mapToDto(saved);
    }

    ;


    @Override
    public DipendenteDTOLight aggiorna(Integer id, DipendenteDTOLight dto) {
        Dipendente esistente = dipendenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        applyDto(esistente, dto);
        return mapToDto(dipendenteRepository.save(esistente));
    }

    ;

    @Override
    public DipendenteDTOLight aggiornaPerCodiceFiscale(String codiceFiscale, DipendenteDTOLight dto) {
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
    public DipendenteDTOLight trovaPerId(Integer id) {
        Dipendente d = dipendenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        return mapToDto(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public DipendenteDTOLight trovaPerCodiceFiscale(String codiceFiscale) {
        Dipendente d = dipendenteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato Codice Fiscale: " + codiceFiscale));
        return mapToDto(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public List<DipendenteDTOLight> trovaTutti() {
        return dipendenteRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    ;


    private void applyDto(Dipendente esistente, DipendenteDTOLight dto) {
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


    private Dipendente mapToEntity(DipendenteDTOLight dto) {
        Dipendente d = new Dipendente();
        applyDto(d, dto);
        return d;
    }

    ;

    private DipendenteDTOLight mapToDto(Dipendente d) {
        DipendenteDTOLight dto = new DipendenteDTOLight();
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



    public Dipendente insertAccountPermesso(String body){
        Dipendente d = new Dipendente();
        JSONObject json = new JSONObject(body);
        d.setNome(json.getString("nome"));
        d.setCognome(json.getString("cognome"));
        d.setCodiceFiscale(json.getString("codiceFiscale"));
        d.setDataNascita(LocalDate.parse(json.getString("dataNascita")));
        d.setDataAssunzione(LocalDate.parse(json.getString("dataAssunzione")));
        d.setStipendio(json.getDouble("stipendio"));
        d.setAreaDiResp(json.getString("areaDiResp"));
        d.setTipoManager(json.getBoolean("tipoManager"));
        Account a = new Account();
        a.setUsername(json.getString("username"));
        a.setPassword(json.getString("password"));
        a.setEmail(json.getString("email"));
        Integer permessoId = json.getInt("permessoId");
        Permesso perm = permessoRepository.findById(permessoId)
                        .orElseThrow( () -> new ResourceNotFoundException("Permesso con id: " + permessoId + "non trovato"));
        System.out.println(perm);
        // d.setAccount(a);

        // a.setPermesso(perm);
        System.out.println(d);
        System.out.print(a);
        return d;
    };


/*
    //Metodi Full
    @Override
    public DipendenteDTOFull creaFull(DipendenteDTOFull dto) {
        Dipendente entity = mapToEntityFull(dto);
        Dipendente saved = dipendenteRepository.save(entity);
        return mapToDtoFull(saved);
    }

    ;

    @Override
    public DipendenteDTOFull aggiornaFull(Integer id, DipendenteDTOFull dto) {
        Dipendente esistente = dipendenteRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        applyDtoFull(esistente, dto);
        Dipendente updated = dipendenteRepository.save(esistente);
        return mapToDtoFull(updated);
    }

    ;

    @Override
    public DipendenteDTOFull aggiornaCfFull(String codiceFiscale, DipendenteDTOFull dto) {
        Dipendente esistente = dipendenteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato CF: " + codiceFiscale));
        applyDtoFull(esistente, dto);
        Dipendente updated = dipendenteRepository.save(esistente);
        return mapToDtoFull(updated);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public DipendenteDTOFull trovaPerIdFull(Integer id) {
        Dipendente d = dipendenteRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato id: " + id));
        return mapToDtoFull(d);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public DipendenteDTOFull trovaPerCodiceFiscaleFull(String codiceFiscale){
        Dipendente d = dipendenteRepository.findByCodiceFiscale(String.valueOf(codiceFiscale))
                .orElseThrow( () -> new ResourceNotFoundException("Dipendente non trovato CF: " + codiceFiscale));
        return mapToDtoFull(d);
    };


    @Override
    @Transactional(readOnly = true)
    public List<DipendenteDTOFull> trovaTuttiFull() {
        return dipendenteRepository.findAll().stream()
                .map(this::mapToDtoFull)
                .collect(Collectors.toList());
    }

    ;


    private void applyDtoFull(Dipendente esistente, DipendenteDTOFull dto) {
        esistente.setNome(dto.getNome());
        esistente.setCognome(dto.getCognome());
        esistente.setCodiceFiscale(dto.getCodiceFiscale());
        esistente.setDataNascita(dto.getDataNascita());
        esistente.setDataAssunzione(dto.getDataAssunzione());
        esistente.setStipendio(dto.getStipendio());
        esistente.setAreaDiResp(dto.getAreaDiResp());
        esistente.setTipoManager(dto.getTipoManager());
        esistente.setRuolo(dto.getRuolo());
        if (dto.getAccount() != null) {
            Account account =
                    .orElseThrow(() -> new ResourceNotFoundException("Account non trovato id:" + dto.getAccount().getId()));
            esistente.setAccount(account);
        }
    }

    ;


    private Dipendente mapToEntityFull(DipendenteDTOFull dto) {
        Dipendente d = new Dipendente();
        applyDtoFull(d, dto);
        return d;
    }

    ;

    private DipendenteDTOFull mapToDtoFull(Dipendente d) {
        DipendenteDTOFull dto = new DipendenteDTOFull();
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
        dto.setAccount(dto.getAccount());
        return dto;
    }

    ; */
}
