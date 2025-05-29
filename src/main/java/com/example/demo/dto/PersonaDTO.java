package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonaDTO {

    private Integer id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private LocalDate dataNascita;
    private String cittaNascita;
    private String indirizzoResidenza;
    private String cittaResidenza;
    private String paeseResidenza;
    private Integer capResidenza;
    private Integer accountId;
}