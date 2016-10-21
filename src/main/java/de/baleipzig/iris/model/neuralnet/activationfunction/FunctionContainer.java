package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

import java.util.function.DoubleFunction;

public class FunctionContainer implements IFunctionContainer {

    //region -- member --

    private DoubleFunction<Double> func;
    private DoubleFunction<Double> derivative;
    private FunctionType type;

    //endregion

    //region -- constructor --

    public FunctionContainer(DoubleFunction<Double> func, DoubleFunction<Double> derivative, FunctionType type) {

        this.func = func;
        this.derivative = derivative;
        this.type = type;
    }

    //endregion

    //region -- methods --

    public DoubleFunction<Double> getFunction() {
        return func;
    }

    public DoubleFunction<Double> getDerivative() {
        return derivative;
    }

    public FunctionType getFunctionType() {
        return type;
    }

    //endregion
}