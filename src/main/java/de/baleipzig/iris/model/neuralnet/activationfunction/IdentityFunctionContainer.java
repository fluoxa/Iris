package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public class IdentityFunctionContainer implements IActivationFunctionContainer {

    private static final ActivationFunction type = ActivationFunction.identity;

    private static Double identity(Double x) {
        return x;
    }

    public DoubleFunction<Double> getActivationFunction() {
        return IdentityFunctionContainer::identity;
    }

    public ActivationFunction getActivationFunctionType() {
        return type;
    }
}
