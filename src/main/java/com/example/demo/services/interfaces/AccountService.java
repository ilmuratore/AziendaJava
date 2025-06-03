package com.example.demo.services.interfaces;


import com.example.demo.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO create(AccountDTO dto);
    AccountDTO update(Long id, AccountDTO dto);
    AccountDTO findById(Long id);
    List<AccountDTO> findAll();
    void delete(Long id);

}