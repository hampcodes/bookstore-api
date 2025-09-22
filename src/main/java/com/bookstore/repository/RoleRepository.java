package com.bookstore.repository;

import com.bookstore.model.Role;
import com.bookstore.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Buscar un rol por su nombre (enum RoleType)
    Optional<Role> findByName(RoleType name);

    // Verificar si existe un rol por su nombre
    boolean existsByName(RoleType name);
}
