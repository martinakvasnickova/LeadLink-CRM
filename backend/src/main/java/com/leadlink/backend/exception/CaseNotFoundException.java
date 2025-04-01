package com.leadlink.backend.exception;

public class CaseNotFoundException extends RuntimeException{
    public CaseNotFoundException(Long id){
        super("Could not found the contact with id " + id);
    }
}
