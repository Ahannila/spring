package com.wartsila.demo.arttuplaygroundapi.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class HandleValidExceptions extends RuntimeException{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> HandleValidExceptions(
            MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldname = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldname, errorMessage);
        });
            return errors;
    }

    public HandleValidExceptions(String message) {super(message); }
}
