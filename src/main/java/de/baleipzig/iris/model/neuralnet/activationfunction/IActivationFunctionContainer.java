package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public interface IActivationFunctionContainer {

    public DoubleFunction<Double> getActivationFunction();

    public ActivationFunction getActivationFunctionType();
}
