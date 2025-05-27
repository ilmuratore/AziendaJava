package com.example.demo.dto;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entities.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DipendenteDTOLight {

    private Integer id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private LocalDate dataNascita;
    private LocalDate dataAssunzione;
    private Double stipendio;
    private String areaDiResp;
    private Boolean tipoManager;
    private String ruolo;
    private Integer accountId;
}