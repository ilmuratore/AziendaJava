package com.example.demo.services;

import com.example.demo.dto.AccountDTOLight;

import java.util.List;

public interface AccountService {
    AccountDTOLight crea(AccountDTOLight dto);
    AccountDTOLight aggiorna(Integer id, AccountDTOLight dto);
    void elimina(Integer id);
    AccountDTOLight trovaPerId(Integer id);
    List<AccountDTOLight> trovaTutti();
}
