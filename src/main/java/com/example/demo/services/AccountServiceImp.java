package com.example.demo.services;


import com.example.demo.config.EncryptionUtils;
import com.example.demo.dto.AccountDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Permesso;
import com.example.demo.exceptions.DuplicateResourceException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.interfaces.AccountService;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PermessoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService {

    @Value("${security.aes.key}")
    private String aesKey;

    private final AccountRepository accountRepository;
    private final PermessoRepository permessoRepository;

    public AccountServiceImp(AccountRepository accountRepository, PermessoRepository permessoRepository) {
        this.accountRepository = accountRepository;
        this.permessoRepository = permessoRepository;
    }

    @Override
    public AccountDTO crea(AccountDTO dto) {
        if (accountRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email già registrata: " + dto.getEmail());
        }
        Permesso p = permessoRepository.findById(dto.getPermessoId())
                .orElseThrow(() -> new ResourceNotFoundException("Permesso non trovato id: " + dto.getPermessoId()));
        Account entity = mapToEntity(dto);
        entity.setPassword(EncryptionUtils.encrypt(dto.getPassword(), aesKey));
        entity.setPermesso(p);
        Account salvato = accountRepository.save(entity);
        return mapToDto(salvato);
    }

    ;

    @Override
    public AccountDTO aggiorna(Integer id, AccountDTO dto) {
        Account esistente = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account non trovato id: " + id));
        esistente.setUsername(dto.getUsername());
        esistente.setPassword( EncryptionUtils.encrypt(dto.getPassword(), aesKey));
        esistente.setEmail(dto.getEmail());
        if (dto.getPermessoId() != null) {
            Permesso p = permessoRepository.findById(dto.getPermessoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Permesso non trovato id: " + dto.getPermessoId()));
            esistente.setPermesso(p);
        }
        Account aggiornato = accountRepository.save(esistente);
        return mapToDto(aggiornato);
    }

    ;

    @Override
    public void elimina(Integer id) {
        if (accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account non trovato id: " + id);
        }
        accountRepository.deleteById(id);
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public AccountDTO trovaPerId(Integer id) {
        Account a = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account non trovato id: " + id));
        AccountDTO dto = mapToDto(a);
        dto.setPassword(EncryptionUtils.decrypt(a.getPassword(), aesKey));
        return dto;
    }

    ;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> trovaTutti() {
        return accountRepository.findAll().stream()
                .map( account -> {
                    AccountDTO dto = mapToDto(account);
                    dto.setPassword(EncryptionUtils.decrypt(account.getPassword(), aesKey));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    ;

    private Account mapToEntity(AccountDTO dto) {
        Account a = new Account();
        a.setUsername(dto.getUsername());
        a.setPassword(dto.getPassword());
        a.setEmail(dto.getEmail());
        return a;
    }

    private AccountDTO mapToDto(Account a) {
        AccountDTO dto = new AccountDTO();
        dto.setId(a.getId());
        dto.setUsername(a.getUsername());
        dto.setEmail(a.getEmail());
        dto.setPermessoId(a.getPermesso().getId());
        return dto;
    }
}

