package com.leadlink.backend.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(Long id) {
        super("Faktura s ID " + id + " nebyla nalezena.");
    }
}
