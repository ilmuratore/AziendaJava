package com.example.demo.repositories;

import com.example.demo.entities.Permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);
    boolean existsByName(String name);

    // Query per trovare permessi di un ruolo specifico
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.id = :roleId")
    Set<Permission> findByRoleId(@Param("roleId") Long roleId);
    // Query per trovare permessi non assegnati a nessun ruolo
    @Query("SELECT p FROM Permission p WHERE p.roles IS EMPTY")
    Page<Permission> findUnassignedPermissions(Pageable pageable);

}
