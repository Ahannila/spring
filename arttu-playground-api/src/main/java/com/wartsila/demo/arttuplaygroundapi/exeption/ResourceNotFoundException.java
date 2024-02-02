package com.wartsila.demo.arttuplaygroundapi.exeption;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 27.6.2023
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
