package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;
import java.util.function.DoubleFunction;

public interface IFunctionContainer {

    DoubleFunction<Double> getFunction();
    DoubleFunction<Double> getDerivative();

    FunctionType getFunctionType();
}
