package com.leadlink.backend.repository;

import com.leadlink.backend.model.ContactEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactEventRepository extends JpaRepository<ContactEvent, Long> {
    List<ContactEvent> findByContactId(Long contactId);
    List<ContactEvent> findByEventId(Long eventId);
}
