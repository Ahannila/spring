package com.wartsila.demo.arttuplaygroundapi.service;

import com.arttuplayground.demolibrary.MathMethods;
import com.arttuplayground.demolibrary.MathMethodsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

@Service
public class MathService {
    /**
     * In the Service package, all the logic will be included. On each Service, you'll use the repository in order to query data.
     * Setting a MathService that won't act on any database, just to set the normal structure.
     */

    MathMethods mathMethods = new MathMethodsImpl();

    public String generateResponseString() {
        return String.format("Hello from the service! Here is your random number ---> %f", generateRandomNumber());
    }
    private double generateRandomNumber() {
        return mathMethods.generateRandom();
    }

    private double divideByTwo() {
        return mathMethods.divideBy2(mathMethods.generateRandom());
    }

    public String divideResponseByTwo() {
        return String.format("This is a randomly generated number that has been divided by 2 ---> %f", divideByTwo());
    }

    private double substract2() {
        return mathMethods.substract2(mathMethods.generateRandom());
    }
}
