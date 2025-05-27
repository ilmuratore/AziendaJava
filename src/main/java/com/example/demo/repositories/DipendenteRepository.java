package com.example.demo.repositories;

import com.example.demo.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Integer> {

    Optional<Dipendente> findByCodiceFiscale(String codiceFiscale);
    boolean existsByCodiceFiscale(String codiceFiscale);
    void deleteByCodiceFiscale(String codiceFiscale);



}
