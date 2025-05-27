package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DipendenteDTOFull {

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
    private String accountUsername;
    private String accountPassword;
    private String accountEmail;
    private Integer permessoId;
    private String permessoTipoPermesso;

}

