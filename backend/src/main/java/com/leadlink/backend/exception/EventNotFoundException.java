package com.leadlink.backend.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Long id){
        super("Could not found the event with id " + id);
    }
}
