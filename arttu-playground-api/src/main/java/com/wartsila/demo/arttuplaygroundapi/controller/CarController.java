package com.wartsila.demo.arttuplaygroundapi.controller;

import com.wartsila.demo.arttuplaygroundapi.model.dto.CarDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarFullDto;
import com.wartsila.demo.arttuplaygroundapi.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.arttuplayground.demolibrary.HelloWorld;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/get-list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCarList() {
        return new ResponseEntity<>(carService.getCarList(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCarById(@PathVariable Long id){
        return carService.searchCar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createCar(@Valid  @RequestBody CarFullDto car) {
        try {
            carService.saveCar(car);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(car, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateCar(@PathVariable Long id, @Valid @RequestBody CarDto car) {
        carService.updateCarById(id ,car);
        return new ResponseEntity<>(car, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/owner/{id}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @PathVariable("ownerId") Long ownerId) {
        carService.updateCarOwner(id, ownerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/hello")
    public void helloWorld() {
        HelloWorld hello = new HelloWorld();
        hello.hello();
    }
}
