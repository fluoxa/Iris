package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;


public class SigmoidFunctionContainer implements IActivationFunctionContainer {

    private static final String name = "sigmoid";

    public static Double sigmoid(Double x) {
        return 1 / (1 + Math.exp(-x));
    }

    @Override
    public DoubleFunction<Double> getActivationFunction() {
        return SigmoidFunctionContainer::sigmoid;
    }

    @Override
    public String getActivationFunctionName() {
        return name;
    }
}
