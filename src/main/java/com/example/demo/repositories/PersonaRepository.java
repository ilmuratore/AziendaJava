package com.example.demo.repositories;

import com.example.demo.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Optional<Persona> findByCodiceFiscale(String codiceFiscale);
    boolean existsByCodiceFiscale(String codiceFiscale);
    void deleteByCodiceFiscale(String codiceFiscale);



}
