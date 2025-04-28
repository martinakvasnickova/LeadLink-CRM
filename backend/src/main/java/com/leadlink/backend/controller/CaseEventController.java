package com.leadlink.backend.controller;

import com.leadlink.backend.dto.CaseEventDTO;
import com.leadlink.backend.dto.CaseEventRequestDTO;
import com.leadlink.backend.model.CaseEvent;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Events;
import com.leadlink.backend.service.CaseEventService;
import com.leadlink.backend.service.CaseService;
import com.leadlink.backend.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case-event")
@CrossOrigin("http://localhost:3000")
public class CaseEventController {

    private final CaseEventService caseEventService;
    private final CaseService caseService;
    private final EventService eventService;

    public CaseEventController(CaseEventService caseEventService, CaseService caseService, EventService eventService) {
        this.caseEventService = caseEventService;
        this.caseService = caseService;
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<CaseEventDTO> addCaseEvent(@RequestBody CaseEventRequestDTO caseEventRequestDTO) {
        Cases cases = caseService.getCaseById(caseEventRequestDTO.getCaseId());
        Events events = eventService.getEventById(caseEventRequestDTO.getEventId());

        CaseEvent caseEvent = new CaseEvent(cases, events);
        CaseEvent savedCaseEvent = caseEventService.addCaseEvent(caseEvent);

        return ResponseEntity.ok(mapToDTO(savedCaseEvent));
    }

    @GetMapping
    public List<CaseEventDTO> getAllCaseEvents() {
        return caseEventService.getAllCaseEvents()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteCaseEvent(@PathVariable Long id) {
        caseEventService.deleteCaseEvent(id);
    }

    @GetMapping("/case/{caseId}")
    public List<CaseEventDTO> getCaseEventsByCase(@PathVariable Long caseId) {
        return caseEventService.getCaseEventsByCase(caseId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/event/{eventId}")
    public List<CaseEventDTO> getCaseEventsByEvent(@PathVariable Long eventId) {
        return caseEventService.getCaseEventsByEvent(eventId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    private CaseEventDTO mapToDTO(CaseEvent caseEvent) {
        return new CaseEventDTO(
                caseEvent.getId(),
                caseEvent.getCases().getId(),
                caseEvent.getCases().getName(),
                caseEvent.getEvent().getId(),
                caseEvent.getEvent().getName()
        );
    }
}
