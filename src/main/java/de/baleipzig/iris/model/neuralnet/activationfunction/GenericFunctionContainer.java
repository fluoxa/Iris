package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;
import java.util.function.DoubleFunction;

public class GenericFunctionContainer extends FunctionContainer {

    //region -- constructors --

    public GenericFunctionContainer(DoubleFunction<Double> func) {
        super(func, null, FunctionType.generic);
    }

    public GenericFunctionContainer(DoubleFunction<Double> func, DoubleFunction<Double> derivative) {
        super(func, derivative, FunctionType.generic);
    }

    //endregion
}