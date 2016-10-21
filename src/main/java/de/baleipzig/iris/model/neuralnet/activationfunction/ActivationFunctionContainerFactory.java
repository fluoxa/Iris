package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

import java.util.function.DoubleFunction;

public class ActivationFunctionContainerFactory {

    public static IFunctionContainer getContainer(FunctionType type){

        switch(type){

            case identity: return new IdentityFunctionContainer();
            case sigmoid: return new SigmoidFunctionContainer();
            default: return new IdentityFunctionContainer();
        }
    }

    public static IFunctionContainer getContainer(DoubleFunction<Double> func, DoubleFunction<Double> derivative){

        return new GenericFunctionContainer(func, derivative);
    }
}