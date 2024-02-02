package com.arttuplayground.demolibrary;

public class MathMethodsImpl implements MathMethods{

    @Override
    public Double generateRandom() {
        return Math.random();
    }

    @Override
    public Double divideBy2(Double number) {
        return number/2;
    }

    @Override
    public Double substract2(Double number) {
        double X = number - 2;
        return X;
    }


}
