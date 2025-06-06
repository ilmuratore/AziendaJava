package com.example.demo.services.interfaces;


import com.example.demo.dto.request.AccountPasswordResetDTO;
import com.example.demo.dto.request.CreateAccountRequestDTO;
import com.example.demo.dto.request.UpdateAccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.dto.response.AccountSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    AccountResponseDTO createAccount(CreateAccountRequestDTO request);

    AccountResponseDTO updateAccount(Long id, UpdateAccountRequestDTO request);

    AccountResponseDTO getAccountById(Long id);

    void deleteAccount(Long id);

    Page<AccountResponseDTO> getAllAccounts(Pageable pageable);

    AccountResponseDTO lockAccount(Long id, AccountSummaryDTO lockInfo);

    AccountResponseDTO unlockAccount(Long id);

    AccountResponseDTO resetPassword(AccountPasswordResetDTO resetInfo);

    AccountResponseDTO verifyEmail(Long id);

    AccountResponseDTO enableAccount(Long id);

    AccountResponseDTO disableAccount(Long id);

    Page<AccountResponseDTO> getAccountsByPersona(Long personaId, Pageable pageable);

    Page<AccountResponseDTO> getAccountsByRole(Long roleId, Pageable pageable);

    Page<AccountResponseDTO> getLockedAccounts(Pageable pageable);

    AccountResponseDTO getAccountByUsername(String username);


}