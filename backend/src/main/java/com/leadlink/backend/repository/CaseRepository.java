package com.leadlink.backend.repository;

import com.leadlink.backend.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Long> {
   List<Case> findByUserUsername(String username);
}
