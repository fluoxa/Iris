package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

public class ExpFunctionContainer extends FunctionContainer {

    public ExpFunctionContainer(){
        super(FunctionCollection::exp, FunctionCollection::exp, FunctionType.EXP);
    }
}