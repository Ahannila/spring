package com.wartsila.demo.arttuplaygroundapi.exeption;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 27.6.2023
 */

@Slf4j
@RestControllerAdvice
public class StandardExceptionHandlingController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    ErrorDto handleResourceNotFound(ResourceNotFoundException e) {
        log.error("RNF ::", e);
        var resourceNotFound = Optional.ofNullable(e.getMessage()).orElse("Resource not found");
        return new ErrorDto(ErrorDto.Codes.RESOURCE_NOT_FOUND, resourceNotFound);
    }

    // EXPLORE THE HTTPSTATUS POSSIBILITY: THERE'S SOMETHING BETTER THEN INTERNAL_SERVER_ERROR.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HandleValidExceptions.class})
    ErrorDto handleResourceNotValid(HandleValidExceptions e) {
        log.error("RNF :: ", e);
        var resourceNotValid = Optional.ofNullable(e.getMessage()).orElse("Resource not valid");
        return new ErrorDto(ErrorDto.Codes.RESOURCE_NOT_FOUND, resourceNotValid);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    ErrorDto handleResourceNotValid(ConstraintViolationException e) {
        log.error("VE :: ", e);
        var resourceNotValid = Optional.ofNullable(e.getMessage()).orElse("Validation exception");
        return new ErrorDto(ErrorDto.Codes.UNHANDLED, resourceNotValid);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RequestNotValidException.class})
    ErrorDto handleResourceNotValid(RequestNotValidException e) {
        log.error("RNV :: ", e);
        var resourceNotValid = Optional.ofNullable(e.getMessage()).orElse("Validation exception");
        return new ErrorDto(ErrorDto.Codes.UNHANDLED, resourceNotValid);
    }
}
