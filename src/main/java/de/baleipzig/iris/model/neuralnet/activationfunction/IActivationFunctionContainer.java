package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public interface IActivationFunctionContainer {

    DoubleFunction<Double> getActivationFunction();

    ActivationFunction getActivationFunctionType();
}
