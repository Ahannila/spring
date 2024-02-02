package com.wartsila.demo.arttuplaygroundapi.service;

import com.github.dozermapper.core.Mapper;
import com.wartsila.demo.arttuplaygroundapi.exeption.HandleValidExceptions;
import com.wartsila.demo.arttuplaygroundapi.exeption.RequestNotValidException;
import com.wartsila.demo.arttuplaygroundapi.exeption.ResourceNotFoundException;
import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerFullDto;
import com.wartsila.demo.arttuplaygroundapi.model.entity.CarEntity;
import com.wartsila.demo.arttuplaygroundapi.model.entity.OwnerEntity;
import com.wartsila.demo.arttuplaygroundapi.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class OwnerService {

    private CarService carService;
    @Autowired
    public void setCarService(CarService carService){
        this.carService = carService;
    }
    public CarService getCarService() {
        return carService;
    }
    @Autowired
    Mapper mapper;
    @Autowired
    OwnerRepository ownerRepository;

    public List<OwnerFullDto> getOwnerList() {
        var res = ownerRepository.findAll().stream()
                .map(current -> mapper.map(current, OwnerFullDto.class))
                .toList();
        return res;
    }

    public void createOwner(OwnerFullDto ownerDto) {
        /**
         * Ensin checkkaus onko listaa autoista, jos ei niin voi jatkaa jos on niin
         * Tähän checkkaus onko ownerin autot olemassa, jos on niin voi luoda omistajan.
         * Onko väliä onko autoilla monta omistajaa?
         */
        OwnerEntity entity = mapper.map(ownerDto, OwnerEntity.class);

        if(checkOwnerCars(entity) ) {
            ownerRepository.save(entity);
        }else {
            throw new RequestNotValidException("Resource not found");
        }
    }

    private boolean checkOwnerCars(OwnerEntity owner) {
        if (owner.getCars() == null) {
            return true;
        }

        int lenght =  owner.getCars().size();
        int lenght1 = 0;

        for (CarEntity car : owner.getCars()) {
            if (car.getId()==null) {
                throw new RequestNotValidException("Owner has car with null value ID");
            } else if(getCarService().carExists(car.getId())){
                lenght1++;
            }
        }

        if(lenght1==lenght) {
            return true;
        }

        throw new RequestNotValidException("Cars of owner not found");
    }

    public void deleteOwner(Long id)  {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Owner not found by Id");
        }
    }

    public OwnerEntity getOwner(Long id) {
        if (ownerExists(id)) {
            return ownerRepository.getReferenceById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public Boolean ownerExists(Long id)  {
        return ownerRepository.existsById(id);
    }
    /**
     * Find user by id, change user names to new owner and just save it, jpa does the rest.
     *
     *
     */
    public ResponseEntity<?> updateOwner(Long id, OwnerDto newOwnerDto) {
        try {
            newOwnerDto.setId(id);
            OwnerEntity newOwner = mapper.map(newOwnerDto, OwnerEntity.class);

            ownerRepository.save(newOwner);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new HandleValidExceptions("Error while updating owner.");
        }
     }
}
