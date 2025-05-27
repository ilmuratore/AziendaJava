package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account", indexes = {
        @Index(name = "idx_email" , columnList = "email", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToOne(mappedBy = "account" , cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    private Dipendente dipendente;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="id_permesso", referencedColumnName="id")
    private Permesso permesso;



}
