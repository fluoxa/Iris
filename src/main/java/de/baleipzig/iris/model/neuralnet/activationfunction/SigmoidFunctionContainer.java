package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

public class SigmoidFunctionContainer extends FunctionContainer {

    public SigmoidFunctionContainer(){
        super(FunctionCollection::sigmoid, FunctionCollection::sigmoidDerivative, FunctionType.SIGMOID);
    }
}