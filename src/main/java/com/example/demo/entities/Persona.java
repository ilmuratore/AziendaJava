package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "persona", indexes = {
        @Index(name = "idx_codice_fiscale", columnList = "codiceFiscale", unique = true),
        @Index(name = "idx_data_nascita", columnList = "dataNascita"),
        @Index(name = "idx_citta_nascita", columnList = "cittaNascita"),
        @Index(name = "idx_citta_residenza", columnList = "cittaResidenza"),
        @Index(name = "idx_paese_residenza", columnList = "paeseResidenza"),
        @Index(name = "idx_cap_residenza", columnList = "capResidenza"),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

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
    private String cittaNascita;

    @Column(nullable = false)
    private String indirizzoResidenza;

    @Column(nullable = false)
    private String cittaResidenza;

    @Column(nullable = false)
    private String paeseResidenza;

    @Column(nullable = false)
    private Integer capResidenza;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_account", nullable = false, unique = true)
    @JsonManagedReference
    private Account account;

}
