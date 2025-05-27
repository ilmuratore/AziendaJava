package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table( name = "dipendente", indexes = {
                @Index(name = "idx_codice_fiscale", columnList = "codiceFiscale" , unique = true),
                @Index(name = "idx_data_nascita", columnList = "dataNascita"),
                @Index(name = "idx_data_assunzione", columnList = "dataAssunzione"),
                @Index(name = "idx_stipendio", columnList = "stipendio"),
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String codiceFiscale;

    @Column(nullable = false)
    private LocalDate dataNascita;

    @Column(nullable = false)
    private LocalDate dataAssunzione;

    @Column(nullable = false)
    private Double stipendio;

    private String areaDiResp;
    private Boolean tipoManager;
    private String ruolo;

    @OneToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "id_account", nullable = false, unique = true)
    @JsonManagedReference
    private Account account;


}
