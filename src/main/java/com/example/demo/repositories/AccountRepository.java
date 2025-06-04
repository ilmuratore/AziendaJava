package com.example.demo.repositories;

import com.example.demo.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository JPA per l’entità Account.
 * Fornisce metodi CRUD di base e tutte le operazioni di query offerte da JpaRepository.
 *
 * JPA repository for the Account entity.
 * Provides basic CRUD methods and all query operations offered by JpaRepository.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
