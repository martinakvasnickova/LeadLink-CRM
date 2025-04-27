package com.leadlink.backend.exception;

import com.leadlink.backend.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Hidden;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Globální zpracovatel výjimek v aplikaci (Zatím implementován pouze pro cases, users a nečekané chyby).
 * Zachytává chyby validace, špatné formáty požadavků a ostatní nečekané chyby.
 */

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Zpracuje chyby validace vstupních dat.
     *
     * @param ex      Výjimka validace
     * @param request WebRequest objekt
     * @return Odpověď obsahující chyby validace
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        logger.warn("Chyba validace: {}", errors);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                null,
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Zpracuje chybný formát JSON požadavku.
     *
     * @param ex      Výjimka čtení JSON
     * @param request WebRequest objekt
     * @return Odpověď informující o chybě formátu
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidFormatException(HttpMessageNotReadableException ex, WebRequest request) {
        logger.error("Chybný formát JSON požadavku: {}", ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON Request - Zkontrolujte správné typy dat",
                null,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Zpracuje všechny nečekané chyby v systému.
     *
     * @param ex      Výjimka
     * @param request WebRequest objekt
     * @return Odpověď o interní chybě serveru
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Neočekávaná chyba: {}", ex.getMessage(), ex);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                null,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Zpracuje chybu nenalezeného případu (Case).
     *
     * @param ex      Výjimka nenalezení případu
     * @param request WebRequest objekt
     * @return Odpověď o nenalezení případu
     */
    @ExceptionHandler(CaseNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCaseNotFound(CaseNotFoundException ex, WebRequest request) {
        logger.error("Případ nenalezen: {}", ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Zpracuje chybu nenalezeného uživatele (User).
     *
     * @param ex      Výjimka nenalezení uživatele
     * @param request WebRequest objekt
     * @return Odpověď o nenalezení uživatele
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        logger.error("Uživatel nenalezen: {}", ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
