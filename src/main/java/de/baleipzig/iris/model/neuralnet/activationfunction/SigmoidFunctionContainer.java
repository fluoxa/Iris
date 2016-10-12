package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public class SigmoidFunctionContainer implements IActivationFunctionContainer {

    private static final ActivationFunction type = ActivationFunction.sigmoid;

    private static Double sigmoid(Double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public DoubleFunction<Double> getActivationFunction() {
        return SigmoidFunctionContainer::sigmoid;
    }

    public ActivationFunction getActivationFunctionType() {
        return type;
    }
}
