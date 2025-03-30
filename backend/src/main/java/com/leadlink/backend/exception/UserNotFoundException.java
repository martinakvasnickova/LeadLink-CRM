package com.leadlink.backend.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id){
        super("Could not found user with id " + id);
    }
}
