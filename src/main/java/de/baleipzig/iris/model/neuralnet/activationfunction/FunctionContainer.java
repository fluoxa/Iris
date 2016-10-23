package de.baleipzig.iris.model.neuralnet.activationfunction;

import de.baleipzig.iris.enums.FunctionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.DoubleFunction;

@Getter
@AllArgsConstructor
public class FunctionContainer implements IFunctionContainer {

    private final DoubleFunction<Double> function;
    private final DoubleFunction<Double> derivative;
    private final FunctionType functionType;
}