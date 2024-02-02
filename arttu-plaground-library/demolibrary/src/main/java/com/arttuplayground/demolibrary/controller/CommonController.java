package com.arttuplayground.demolibrary.controller;

import com.arttuplayground.demolibrary.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 30.8.2023
 */

@Slf4j
@RestController
@RequestMapping("/api")
@ConditionalOnProperty(name = "common.services.enable.sample-endpoint", havingValue = "true", matchIfMissing = true)
public class CommonController {

    @Autowired
    CommonService service;

    @GetMapping(path = "${common.api.endpoint}" + "/get-all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> databaseInterface() {
        log.info("Called library database interface!");
        var res = service.getAllEntities();
        log.info("Recovered " + res.size() + " entities.");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}
