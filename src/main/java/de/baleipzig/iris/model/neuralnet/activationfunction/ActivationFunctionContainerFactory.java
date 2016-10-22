package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

import java.util.function.DoubleFunction;

public class ActivationFunctionContainerFactory {

    public static IFunctionContainer create(FunctionType type){

        switch(type){

            case IDENTITY: return new IdentityFunctionContainer();
            case SIGMOID: return new SigmoidFunctionContainer();
            default: return new IdentityFunctionContainer();
        }
    }

    public static IFunctionContainer create(DoubleFunction<Double> func, DoubleFunction<Double> derivative){

        return new GenericFunctionContainer(func, derivative);
    }
}