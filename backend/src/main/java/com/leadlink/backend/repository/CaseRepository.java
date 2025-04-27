package com.leadlink.backend.repository;


import com.leadlink.backend.model.Cases;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface pro entitu Cases.
 * Poskytuje základní CRUD operace a vlastní dotazy nad případy.
 */

public interface CaseRepository extends JpaRepository<Cases, Long> {

   /**
    * Vrátí seznam případů spojených s uživatelem podle jeho username.
    *
    * @param username Uživatelské jméno vlastníka případů
    * @return Seznam případů patřících danému uživateli
    */
   List<Cases> findByUserUsername(String username);
}
