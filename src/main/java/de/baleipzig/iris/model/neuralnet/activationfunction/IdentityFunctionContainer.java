package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;

public class IdentityFunctionContainer extends FunctionContainer {

    public IdentityFunctionContainer(){
        super(FunctionCollection::identity, FunctionCollection::unit, FunctionType.IDENTITY);
    }
}