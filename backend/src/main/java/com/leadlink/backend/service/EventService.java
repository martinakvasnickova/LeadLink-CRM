package com.leadlink.backend.service;

import com.leadlink.backend.exception.EventNotFoundException;
import com.leadlink.backend.model.Events;
import com.leadlink.backend.model.Users;
import com.leadlink.backend.repository.EventRepository;
import com.leadlink.backend.repository.UserRepository;
import com.leadlink.backend.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Events createEvent(Events eventEntity){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Users user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        eventEntity.setUser(user);
        return eventRepository.save(eventEntity);
    }

    public List<Events> getAllEvents(){
        return eventRepository.findAll();
    }

    public Events getEventById(Long id){
        return eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException(id));
    }

    public Events updateEvent(Long id, Events newEvent){
        return eventRepository.findById(id)
                .map(eventEntity ->{
                    eventEntity.setName(newEvent.getName());
                    eventEntity.setCreatedAt(newEvent.getCreatedAt());
                    eventEntity.setStartAt(newEvent.getStartAt());
                    eventEntity.setEndAt(newEvent.getEndAt());
                    return eventRepository.save(eventEntity);
                }).orElseThrow(()->new EventNotFoundException(id));
    }

    public void deleteEvent(Long id){
        if(!eventRepository.existsById(id)){
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }

    public List<Events>getEventsForCurrentUser(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();
        return eventRepository.findByUserUsername(username);
    }
}
