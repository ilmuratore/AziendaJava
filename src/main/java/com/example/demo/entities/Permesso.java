package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permessi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_permesso" , nullable = false)
    private TipoPermesso tipoPermesso;

    @OneToMany(mappedBy = "permesso", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Account> account = new ArrayList<>();

}
