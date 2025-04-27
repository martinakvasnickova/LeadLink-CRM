package com.leadlink.backend.repository;

import com.leadlink.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pro přístup k entitě Users v databázi.
 */

public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * Najde uživatele podle jeho uživatelského jména.
     *
     * @param username Uživatelské jméno
     * @return Uživatel s daným uživatelským jménem
     */
    Users findByUsername(String username);
}
