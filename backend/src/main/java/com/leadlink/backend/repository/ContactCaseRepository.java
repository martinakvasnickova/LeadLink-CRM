package com.leadlink.backend.repository;

import com.leadlink.backend.model.ContactCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactCaseRepository extends JpaRepository<ContactCase, Long> {
    List<ContactCase> findByContactId(Long contactId);
    List<ContactCase> findByCasesId(Long casesId);
}
