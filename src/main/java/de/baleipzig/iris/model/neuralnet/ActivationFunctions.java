package de.baleipzig.iris.model.neuralnet;

public class ActivationFunctions {

    public static Double identity (Double x){
        return x;
    }

    public static Double sigmoid (Double x){
        return 1/(1+Math.exp(-x));
    }
}