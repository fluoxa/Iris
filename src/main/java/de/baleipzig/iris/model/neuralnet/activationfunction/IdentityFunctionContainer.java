package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public class IdentityFunctionContainer implements IActivationFunctionContainer {

    private static final String name = "identity";

    private static Double identity(Double x) {
        return x;
    }

    public DoubleFunction<Double> getActivationFunction() {
        return IdentityFunctionContainer::identity;
    }

    public String getActivationFunctionName() {
        return name;
    }
}
