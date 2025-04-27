package com.leadlink.backend.exception;

/**
 * Výjimka vyhazovaná, pokud není nalezen obchodní případ (Case).
 */

public class CaseNotFoundException extends RuntimeException{

    /**
     * Konstruktor výjimky.
     *
     * @param id ID případu, který nebyl nalezen
     */
    public CaseNotFoundException(Long id){
        super("Could not found the contact with id " + id);
    }
}
