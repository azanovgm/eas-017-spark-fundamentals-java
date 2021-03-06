package com.luxoft.training.eas017.day2.pi;

import static java.lang.Math.random;

public class LocalPiComputationJava {

    public static void main(String[] args) {

        int numberOfIterations = 1_000_000;

        int pointsInsideCircle = 0;

        for (int i = 0; i < numberOfIterations; i++){
            double x = random() * 2 - 1;
            double y = random() * 2 - 1;
            if (x * x + y * y < 1) pointsInsideCircle += 1;
        }
        
        double piApproximation = 4.0 * pointsInsideCircle / numberOfIterations;

        System.out.println("Pi is roughly " + piApproximation);
    }
}
