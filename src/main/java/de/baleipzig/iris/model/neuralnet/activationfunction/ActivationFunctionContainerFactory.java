package de.baleipzig.iris.model.neuralnet.activationfunction;

import java.util.function.DoubleFunction;

public class ActivationFunctionContainerFactory {

    public static IActivationFunctionContainer getContainer(ActivationFunction type){

        switch(type){

            case identity: return new IdentityFunctionContainer();
            case sigmoid: return new SigmoidFunctionContainer();
            default: return new IdentityFunctionContainer();
        }
    }

    public static IActivationFunctionContainer getContainer(DoubleFunction func){

        return new GenericFunctionContainer(func);
    }
}