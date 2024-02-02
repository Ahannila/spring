package com.wartsila.demo.arttuplaygroundapi.exeption;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 27.6.2023
 */
public class RequestNotValidException extends RuntimeException {

    public RequestNotValidException() {
    }

    public RequestNotValidException(String message) {
        super(message);
    }
}
