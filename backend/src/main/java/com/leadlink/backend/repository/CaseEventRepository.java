package com.leadlink.backend.repository;

import com.leadlink.backend.model.CaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CaseEventRepository extends JpaRepository<CaseEvent, Long> {
    List<CaseEvent> findByCasesId(Long caseId);
    List<CaseEvent> findByEventId(Long eventId);
}
