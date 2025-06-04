package com.example.demo.entities;

import com.example.demo.entities.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * Rappresenta una persona (dipendente o utente) nel sistema.
 * Represents a person (employee or user) in the system.
 */
@Schema(name = "Persona", description = "Entità che rappresenta una persona | Entity representing a person")
@Entity
@Table(name = "personas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    /**
     * Identificativo della persona.
     * Person identifier.
     */
    @Schema(description = "ID della persona | Person ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome della persona.
     * First name of the person.
     */
    @Schema(description = "Nome | First name", example = "Mario", required = true)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Cognome della persona.
     * Last name of the person.
     */
    @Schema(description = "Cognome | Last name", example = "Rossi", required = true)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    /**
     * Data di nascita.
     * Birth date.
     */
    @Schema(description = "Data di nascita | Birth date", example = "1980-01-01")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Genere (enum).
     * Gender (enum).
     */
    @Schema(description = "Genere | Gender")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    /**
     * Codice fiscale (univoco).
     * Tax code (unique).
     */
    @Schema(description = "Codice fiscale | Tax code", example = "RSSMRA80A01H501U")
    @Column(name = "tax_code", length = 16, unique = true)
    private String taxCode;

    /**
     * Indirizzo.
     * Address.
     */
    @Schema(description = "Indirizzo | Address")
    @Column(length = 255)
    private String address;

    /**
     * Città.
     * City.
     */
    @Schema(description = "Città | City")
    @Column(length = 100)
    private String city;

    /**
     * CAP.
     * Postal code.
     */
    @Schema(description = "Codice postale | Postal code", example = "20100")
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    /**
     * Paese.
     * Country.
     */
    @Schema(description = "Paese | Country")
    @Column(length = 100)
    private String country;

    /**
     * Numero di telefono.
     * Phone number.
     */
    @Schema(description = "Numero di telefono | Phone number", example = "+39 02 12345678")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /**
     * Email (univoca).
     * Email (unique).
     */
    @Schema(description = "Email | Email", example = "mario.rossi@example.com")
    @Column(length = 100, unique = true)
    private String email;

    /**
     * Data di assunzione.
     * Hire date.
     */
    @Schema(description = "Data di assunzione | Hire date")
    @Column(name = "hire_date")
    private LocalDate hireDate;

    /**
     * Data di termine rapporto.
     * Termination date.
     */
    @Schema(description = "Data di termine rapporto | Termination date")
    @Column(name = "termination_date")
    private LocalDate terminationDate;

    /**
     * Timestamp di creazione (readonly).
     * Creation timestamp (readonly).
     */
    @Schema(description = "Timestamp di creazione | Creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    /**
     * Timestamp di aggiornamento (readonly).
     * Update timestamp (readonly).
     */
    @Schema(description = "Timestamp di aggiornamento | Update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Account associato.
     * Associated account.
     */
    @Schema(description = "Account associato | Associated account")
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Account account;

    /**
     * Dipartimento di appartenenza.
     * Department of affiliation.
     */
    @Schema(description = "Dipartimento | Department")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * Posizione lavorativa.
     * Job position.
     */
    @Schema(description = "Posizione lavorativa | Job position")
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    /**
     * Team di cui fa parte.
     * Teams of which the person is a member.
     */
    @Schema(description = "Team di appartenenza | Teams membership")
    @ManyToMany
    @JoinTable(
            name = "persona_team",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    /**
     * Progetti assegnati.
     * Assigned projects.
     */
    @Schema(description = "Progetti assegnati | Assigned projects")
    @ManyToMany
    @JoinTable(
            name = "persona_project",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

}
