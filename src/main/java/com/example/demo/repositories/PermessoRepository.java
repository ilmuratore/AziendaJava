package com.example.demo.repositories;

import com.example.demo.entities.Permesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermessoRepository extends JpaRepository<Permesso, Integer> {
    Permesso findByTipoPermesso(String tipoPermesso);

}
