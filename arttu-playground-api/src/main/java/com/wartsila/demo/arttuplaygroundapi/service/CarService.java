package com.wartsila.demo.arttuplaygroundapi.service;


import com.arttuplayground.demolibrary.controller.CommonController;
import com.github.dozermapper.core.Mapper;
import com.wartsila.demo.arttuplaygroundapi.exeption.RequestNotValidException;
import com.wartsila.demo.arttuplaygroundapi.exeption.ResourceNotFoundException;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarFullDto;
import com.wartsila.demo.arttuplaygroundapi.model.entity.CarEntity;
import com.wartsila.demo.arttuplaygroundapi.model.entity.OwnerEntity;
import com.wartsila.demo.arttuplaygroundapi.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

@Service
public class CarService {

    @Autowired
    Mapper mapper;

    private OwnerService ownerService;
    @Autowired
    public void setOwnerService(@Lazy OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    public OwnerService getOwnerService() {
        return ownerService;
    }

    @Autowired
    CarRepository carRepository;


    public List<CarFullDto> getCarList() {
        var res = carRepository.findAll().stream()
                .map(current -> mapper.map(current, CarFullDto.class))
                .toList();
        System.out.println(res);
        return res;
    }



    private Boolean checkCarYearBrandAndOwner(CarFullDto car) {
        if(car.getYear()>1950 && car.getBrand() != "" && checkCarOwner(car)) {
            return true;
        }
        return false;
    }

    public Boolean checkCarOwner(CarFullDto car) {

        if(car.getOwner() == null){
            return true;
        }else if(ownerService.ownerExists(car.getOwner().getId())){
            return true;
        }else {
            throw new RequestNotValidException("");
        }
    }

    public void saveCar(CarFullDto carDto) {
        if (checkCarYearBrandAndOwner(carDto)) {
            CarEntity entityToCreate = mapper.map(carDto, CarEntity.class);
            carRepository.save(entityToCreate);
        } else {
            throw new RequestNotValidException("Brand/Year/owner not acceptable");
        }

    }

    public ResponseEntity<?> searchCar(Long id){
        if(carExists(id)){
            return new ResponseEntity<>(carRepository.findById(id), HttpStatus.ACCEPTED);
        } else {
            throw new ResourceNotFoundException("Car not found by ID");
        }

    }

    // AS COMMENTED IN THE CONTROLLER, THIS IS AN UPDATE, NOT A REPLACE

    public void updateCarById(Long id, CarDto newCar) {

        if(carExists(id)) {
            carRepository.findById(id) .map(car ->{
                car.setBrand(newCar.getBrand());
                car.setYear(newCar.getYear());
                return carRepository.save(car);
            });
        }else {
            throw new ResourceNotFoundException("Car id not found");
        }
    }

    public void updateCarOwner(Long id, Long ownerId) {
        OwnerEntity newOwner = getOwnerService().getOwner(ownerId);

        if(carExists(id)) {
            carRepository.findById(id) .map(car ->{
                car.setOwner(newOwner);
                return carRepository.save(car);
            });
        }else {
            throw new ResourceNotFoundException("Car Id not found");
        }
    }

    public Boolean carExists(Long id) {
        return carRepository.existsById(id);
    }

    public void deleteCarById(Long id) {
        if(carExists(id)){
            carRepository.deleteById(id);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id doesn't match any car");
        }
    }
}
