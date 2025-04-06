package com.leadlink.backend.controller;

import com.leadlink.backend.model.Events;
import com.leadlink.backend.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public Events newEvent(@RequestBody Events newEvent){
        return eventService.createEvent(newEvent);
    }

    @GetMapping
    public List<Events> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Events getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }

    @PutMapping("/{id}")
    public Events updateEvent(@RequestBody Events newEvent, @PathVariable Long id){
        return eventService.updateEvent(id, newEvent);
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
}
