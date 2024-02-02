package com.arttuplayground.demolibrary;

import org.junit.Test;
import static org.junit.Assert.*;

import com.arttuplayground.demolibrary.MathMethodsImpl;

public class MathMethodsTest {

    private MathMethodsImpl mathMethods = new MathMethodsImpl();
    @Test
    public void divideBy2returnsCorrect() {
        double num = 8;
        Double res = mathMethods.divideBy2(num);
        assertEquals(4, res, 0.001);
    }

    @Test
    public void substract2ReturnsCorrect() {
        double num = 4.5;
        Double res = mathMethods.substract2(num);
        assertEquals(2.5, res, 0.001);
        
    }

    @Test
    public void generateRandomNumberWorks() {
        Double res = mathMethods.generateRandom();
        assertFalse(Double.isNaN(res));
    }


}
