package com.leadlink.backend.controller;

import com.leadlink.backend.dto.EventDTO;
import com.leadlink.backend.dto.EventRequestDTO;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Events;
import com.leadlink.backend.service.CaseService;
import com.leadlink.backend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/event")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final CaseService caseService; // Nové!

    public EventController(EventService eventService, CaseService caseService) {
        this.eventService = eventService;
        this.caseService = caseService;
    }

    @PostMapping
    public EventDTO newEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO) {
        logger.info("Vytvářím nový event: {}", eventRequestDTO.getName());

        Events newEvent = new Events();
        newEvent.setName(eventRequestDTO.getName());
        newEvent.setStartAt(eventRequestDTO.getStartAt());
        newEvent.setEndAt(eventRequestDTO.getEndAt());
        newEvent.setCreatedAt(LocalDateTime.now());

        if (eventRequestDTO.getCaseId() != null) {
            Cases selectedCase = caseService.getCaseById(eventRequestDTO.getCaseId());
            newEvent.setCases(selectedCase);
        }

        Events savedEvent = eventService.createEvent(newEvent);
        return mapToDTO(savedEvent);
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        logger.info("Načítám všechny eventy");
        return eventService.getAllEvents().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        logger.info("Načítám event s ID {}", id);
        Events event = eventService.getEventById(id);
        return mapToDTO(event);
    }

    @PutMapping("/{id}")
    public EventDTO updateEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO, @PathVariable Long id) {
        logger.info("Aktualizuji event s ID {}", id);

        Events updatedEvent = new Events();
        updatedEvent.setName(eventRequestDTO.getName());
        updatedEvent.setStartAt(eventRequestDTO.getStartAt());
        updatedEvent.setEndAt(eventRequestDTO.getEndAt());
        updatedEvent.setCreatedAt(LocalDateTime.now());

        if (eventRequestDTO.getCaseId() != null) {
            Cases selectedCase = caseService.getCaseById(eventRequestDTO.getCaseId());
            updatedEvent.setCases(selectedCase);
        }

        Events savedEvent = eventService.updateEvent(id, updatedEvent);
        return mapToDTO(savedEvent);
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id) {
        logger.warn("Mažu event s ID {}", id);
        eventService.deleteEvent(id);
        return "Event with id " + id + " has been deleted successfully";
    }

    @GetMapping("/user")
    public List<EventDTO> getEventsForCurrentUser() {
        logger.info("Načítám eventy pro aktuálního uživatele");
        return eventService.getEventsForCurrentUser().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private EventDTO mapToDTO(Events event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getCreatedAt(),
                event.getStartAt(),
                event.getEndAt(),
                event.getUser() != null ? event.getUser().getUsername() : null,
                event.getCases() != null ? event.getCases().getId() : null,
                event.getCases() != null ? event.getCases().getName() : null
        );
    }
}
