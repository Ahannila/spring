package com.wartsila.demo.arttuplaygroundapi.controller;

import com.wartsila.demo.arttuplaygroundapi.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

@RestController
@RequestMapping("/api/")
public class DemoController {

    @Autowired
    MathService mathService;

    @GetMapping("/random-number")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getListAutomaticAcquisition() {

        return new ResponseEntity<>(mathService.generateResponseString(), HttpStatus.ACCEPTED);
    }
}
