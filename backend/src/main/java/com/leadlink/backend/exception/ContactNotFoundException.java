package com.leadlink.backend.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(Long id){
        super("Could not found the user with id " + id);
    }
}
