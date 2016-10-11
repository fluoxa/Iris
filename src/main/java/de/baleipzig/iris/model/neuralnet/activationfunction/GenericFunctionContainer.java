package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public class GenericFunctionContainer implements IActivationFunctionContainer {

    //region -- memebers --

    private static final ActivationFunction type = ActivationFunction.generic;

    private DoubleFunction<Double> generic;

    //endregion

    //region -- constructors --

    public GenericFunctionContainer(DoubleFunction<Double> func){
        this.generic = func;
    }

    //endregion

    //region -- methods --

    public DoubleFunction<Double> getActivationFunction() {
        return generic;
    }
    public ActivationFunction getActivationFunctionType() {
        return type;
    }

    //endregion
}