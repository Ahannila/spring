package com.wartsila.demo.arttuplaygroundapi.controller;

import com.wartsila.demo.arttuplaygroundapi.model.dto.GarageFullDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 11.7.2023
 */

public interface IGarageController {
    ResponseEntity<?> getGarageList();
    ResponseEntity<?> createGarage(@Valid @RequestBody GarageFullDto owner);
    ResponseEntity<?> deleteGarage(@PathVariable Long id);
    ResponseEntity<?> updateGarage(@PathVariable Long id, @RequestBody GarageFullDto newGarage);
    ResponseEntity<?> occupyGarage(@PathVariable Long garageId, @PathVariable Long carId);
    ResponseEntity<?> freeUpGarage(@PathVariable Long garageId);
}
