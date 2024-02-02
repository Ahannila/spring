package com.wartsila.demo.arttuplaygroundapi.service;

import com.wartsila.demo.arttuplaygroundapi.exeption.RequestNotValidException;
import com.wartsila.demo.arttuplaygroundapi.exeption.ResourceNotFoundException;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarFullDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.GarageDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.GarageFullDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 11.7.2023
 */

public interface IGarageService {
    List<GarageFullDto> getGarageList();
    ResponseEntity createGarage(GarageFullDto garageDto) throws RequestNotValidException;
    ResponseEntity deleteGarage(Long id) throws ResourceNotFoundException;
    ResponseEntity updateGarage(Long id, GarageFullDto newGarage) throws RequestNotValidException, ResourceNotFoundException;
    ResponseEntity occupyGarage(Long garageId, Long carId) throws RequestNotValidException, ResourceNotFoundException;
    ResponseEntity freeUpGarage(Long garageId) throws ResourceNotFoundException;
}
