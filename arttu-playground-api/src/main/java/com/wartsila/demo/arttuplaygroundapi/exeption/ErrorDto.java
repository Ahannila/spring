package com.wartsila.demo.arttuplaygroundapi.exeption;

import lombok.Value;

import java.time.Instant;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 27.6.2023
 */

@Value
public class ErrorDto {

    Instant timestamp = Instant.now();
    String code;
    String message;

    public ErrorDto(String message) {
        this.code = Codes.GENERAL.name();
        this.message = message;
    }

    public ErrorDto(Codes code, String message) {
        this.code = code.name();
        this.message = message;
    }

    public enum Codes {
        FORBIDDEN,
        GENERAL,
        RESOURCE_NOT_FOUND,
        USER_NOT_LOGGED,
        USER_BELONGS_TO_PLANT,
        USER_NOT_ADMIN,
        UNHANDLED,
        CSV_EXCEPTION,
        ENTITY_ALREADY_EXISTING,
        SERVICE_UNAVAILABLE
    }
}
