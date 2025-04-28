package com.leadlink.backend.service;

import com.leadlink.backend.model.CaseEvent;
import com.leadlink.backend.repository.CaseEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseEventService {
    private final CaseEventRepository caseEventRepository;

    public CaseEventService(CaseEventRepository caseEventRepository) {
        this.caseEventRepository = caseEventRepository;
    }

    public CaseEvent addCaseEvent(CaseEvent caseEvent) {
        return caseEventRepository.save(caseEvent);
    }

    public List<CaseEvent> getAllCaseEvents() {
        return caseEventRepository.findAll();
    }

    public List<CaseEvent> getCaseEventsByCase(Long caseId) {
        return caseEventRepository.findByCasesId(caseId);
    }

    public List<CaseEvent> getCaseEventsByEvent(Long eventId) {
        return caseEventRepository.findByEventId(eventId);
    }

    public void deleteCaseEvent(Long id) {
        caseEventRepository.deleteById(id);
    }
}
