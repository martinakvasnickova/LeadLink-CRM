package com.leadlink.backend.repository;


import com.leadlink.backend.model.Cases;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseRepository extends JpaRepository<Cases, Long> {
   List<Cases> findByUserUsername(String username);
}
