package com.wartsila.demo.arttuplaygroundapi.controller;

import com.wartsila.demo.arttuplaygroundapi.model.dto.GarageFullDto;
import com.wartsila.demo.arttuplaygroundapi.service.GarageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/garage")
public class GarageController implements IGarageController {

    @Autowired
    private GarageService garageService;
    @Override
    @GetMapping("/get-list")
    public ResponseEntity<?> getGarageList() {
        return new ResponseEntity<>(garageService.getGarageList(),HttpStatus.ACCEPTED);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> createGarage(@Valid GarageFullDto owner) {
        try {
            garageService.createGarage(owner);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGarage(@PathVariable Long id) {
        try {
            return garageService.deleteGarage(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGarage(@PathVariable Long id,@RequestBody @Valid GarageFullDto newGarage) {
        try {
            return garageService.updateGarage(id, newGarage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PostMapping("/occupy/{garageId}/{carId}")
    public ResponseEntity<?> occupyGarage(@PathVariable Long garageId,@PathVariable Long carId) {
        return garageService.occupyGarage(garageId,carId);
    }

    @Override
    @PutMapping("/free-garage/{garageId}")
    public ResponseEntity<?> freeUpGarage(@PathVariable Long garageId) {
        return garageService.freeUpGarage(garageId);
    }
}
