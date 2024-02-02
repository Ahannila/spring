package com.wartsila.demo.arttuplaygroundapi.controller;

import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerFullDto;
import com.wartsila.demo.arttuplaygroundapi.service.OwnerService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/get-list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getOwnerList() {

        return new ResponseEntity<>(ownerService.getOwnerList(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addOwner(@Valid @RequestBody OwnerFullDto owner){
        ownerService.createOwner(owner);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody OwnerDto newOwner){
        return ownerService.updateOwner(id, newOwner);
    }
}
