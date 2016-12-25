package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

import java.util.function.DoubleFunction;

public class ActivationFunctionContainerFactory {

    public static IFunctionContainer create(FunctionType type){

        switch(type){

            case IDENTITY: return new IdentityFunctionContainer();
            case SIGMOID: return new SigmoidFunctionContainer();
            case EXP: return new ExpFunctionContainer();
            case NONE: return new IdentityFunctionContainer();
            default: throw new RuntimeException("Unknown FunctionType " + type.toString());
        }
    }

    public static IFunctionContainer create(DoubleFunction<Double> func, DoubleFunction<Double> derivative){

        return new GenericFunctionContainer(func, derivative);
    }
}