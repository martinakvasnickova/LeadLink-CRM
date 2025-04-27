package com.leadlink.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class EventNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(EventNotFoundException exception){
        Map<String, String> errorMap = new HashMap<>();
        return errorMap;
    }
}
