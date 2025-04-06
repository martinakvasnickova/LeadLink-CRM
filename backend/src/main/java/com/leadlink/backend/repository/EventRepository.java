package com.leadlink.backend.repository;

import com.leadlink.backend.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Events, Long> {
    List<Events> findByUserUsername(String username);
}
