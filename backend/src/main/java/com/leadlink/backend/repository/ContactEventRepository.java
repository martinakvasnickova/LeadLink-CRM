package com.leadlink.backend.repository;

import com.leadlink.backend.model.ContactEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactEventRepository extends JpaRepository<ContactEvent, Long> {
    
}
