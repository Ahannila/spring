package com.wartsila.demo.arttuplaygroundapi.service;

import com.github.dozermapper.core.Mapper;
import com.wartsila.demo.arttuplaygroundapi.exeption.RequestNotValidException;
import com.wartsila.demo.arttuplaygroundapi.exeption.ResourceNotFoundException;
import com.wartsila.demo.arttuplaygroundapi.model.dto.GarageFullDto;
import com.wartsila.demo.arttuplaygroundapi.model.entity.CarEntity;
import com.wartsila.demo.arttuplaygroundapi.model.entity.GarageEntity;
import com.wartsila.demo.arttuplaygroundapi.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageService implements IGarageService{
    @Autowired
    Mapper mapper;
    @Autowired
    GarageRepository garageRepository;

    private CarService carService;
    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
    public CarService getCarService() {return carService;}

    @Override
    public List<GarageFullDto> getGarageList() {
        var res = garageRepository.findAll().stream()
                .map(current -> mapper.map(current, GarageFullDto.class))
                .toList();
        //System.out.println(res);
        return res;
    }

    @Override
    public ResponseEntity createGarage(GarageFullDto garageDto) throws RequestNotValidException {
        GarageEntity entityToCreate = mapper.map(garageDto, GarageEntity.class);
        return new ResponseEntity<>(garageRepository.save(entityToCreate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteGarage(Long id) throws ResourceNotFoundException {
        if (garageExists(id)) {
            garageRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ResourceNotFoundException("Garage not found by id");
        }
    }

    @Override
    public ResponseEntity updateGarage(Long id, GarageFullDto newGarage) throws RequestNotValidException, ResourceNotFoundException {
        if (garageExists(id)) {
            garageRepository.findById(id).map(garage -> {
                garage.setName(newGarage.getName());
                garage.setLocation(newGarage.getLocation());
                return new ResponseEntity<>(garageRepository.save(garage), HttpStatus.ACCEPTED);
            });
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ResourceNotFoundException("Garage id not valid");
        }
    }

    @Override
    public ResponseEntity occupyGarage(Long garageId, Long carId) throws RequestNotValidException, ResourceNotFoundException {

        if(!carService.carExists(carId)) {
            throw new ResourceNotFoundException("Car id not found");
        }

        CarEntity carEntity = getCarService().carRepository.getReferenceById(carId);

        if(garageExists(garageId)) {
            garageRepository.findById(garageId) .map(garage -> {
                garage.setCar(carEntity);
                return new ResponseEntity(garageRepository.save(garage), HttpStatus.ACCEPTED);
            });
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ResourceNotFoundException("Car or garage id not found");
        }
    }

    @Override
    public ResponseEntity freeUpGarage(Long garageId) throws ResourceNotFoundException {
        if (garageExists(garageId)) {
            GarageEntity garage = garageRepository.getReferenceById(garageId);

            if (garage.getCar() == null) {
                throw new ResourceNotFoundException("Car id not found");
            }

            garage.setCar(null);
            garageRepository.save(garage);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        throw new ResourceNotFoundException("Garage id not found");
    }

    private boolean garageExists(Long id) {
        return garageRepository.existsById(id);
    }
}
