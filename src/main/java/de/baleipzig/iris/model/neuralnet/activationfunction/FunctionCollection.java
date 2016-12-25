package de.baleipzig.iris.model.neuralnet.activationfunction;

public class FunctionCollection {

    public static Double unit (Double x) { return 1.; }

    public static Double identity (Double x) { return x; }

    public static Double exp (Double x) { return Math.exp(x); }

    public static Double sigmoid(Double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static Double sigmoidDerivative(Double x) {
        return Math.exp(x)/((Math.exp(x)+1)*(Math.exp(x)+1));
    }
}