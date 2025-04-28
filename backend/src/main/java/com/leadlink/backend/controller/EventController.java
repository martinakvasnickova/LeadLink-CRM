package com.leadlink.backend.controller;

import com.leadlink.backend.dto.EventDTO;
import com.leadlink.backend.dto.EventRequestDTO;
import com.leadlink.backend.model.Events;
import com.leadlink.backend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/event")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
    @PostMapping
    public Events newEvent(@RequestBody Events newEvent){
        return eventService.createEvent(newEvent);
    }
     **/

    @PostMapping
    public EventDTO newEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO) {
        logger.info("Vytvářím nový event: {}", eventRequestDTO.getName());
        Events newEvent = new Events();
        newEvent.setName(eventRequestDTO.getName());
        newEvent.setStartAt(eventRequestDTO.getStartAt());
        newEvent.setEndAt(eventRequestDTO.getEndAt());
        newEvent.setCreatedAt(LocalDateTime.now());
        Events savedEvent = eventService.createEvent(newEvent);
        return mapToDTO(savedEvent);
    }

    @GetMapping
    public List<Events> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Events getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }

    /**
    @PutMapping("/{id}")
    public Events updateEvent(@RequestBody Events newEvent, @PathVariable Long id){
        return eventService.updateEvent(id, newEvent);
    }
     **/

    @PutMapping("/{id}")
    public EventDTO updateEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO, @PathVariable Long id) {
        logger.info("Aktualizuji event s ID {}", id);
        Events updatedEvent = new Events();
        updatedEvent.setName(eventRequestDTO.getName());
        updatedEvent.setStartAt(eventRequestDTO.getStartAt());
        updatedEvent.setEndAt(eventRequestDTO.getEndAt());
        updatedEvent.setCreatedAt(LocalDateTime.now());
        Events savedEvent = eventService.updateEvent(id, updatedEvent);
        return mapToDTO(savedEvent);
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return "Event with id " + id + " has been deleted successfully";
    }

    @GetMapping("/user")
    public List<Events> getEventsForCurrentUser(){
        return eventService.getEventsForCurrentUser();
    }

    private EventDTO mapToDTO(Events event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getCreatedAt(),
                event.getStartAt(),
                event.getEndAt(),
                event.getUser() != null ? event.getUser().getUsername() : null
        );
    }
}
