package com.example.demo.interfaces;

import com.example.demo.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO crea(AccountDTO dto);
    AccountDTO aggiorna(Integer id, AccountDTO dto);
    void elimina(Integer id);
    AccountDTO trovaPerId(Integer id);
    List<AccountDTO> trovaTutti();
}
