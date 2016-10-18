package de.baleipzig.iris.model.neuralnet.node;

import de.baleipzig.iris.model.neuralnet.activationfunction.ActivationFunction;
import de.baleipzig.iris.model.neuralnet.activationfunction.IActivationFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.function.DoubleFunction;

public class Node implements INode {

    //region -- member --

    @Getter
    private ArrayList<IAxon> parentAxons;

    @Getter
    private ArrayList<IAxon> childAxons;

    @Getter
    @Setter
    private double activation;

    @Getter
    @Setter
    private double bias;

    @Getter
    @Setter
    private double error;

    @Getter
    @Setter
    private double weightedInput;

    private IActivationFunctionContainer activationFunctionContainer;

    //endregion

    //region -- constructor --

    public Node(){

        parentAxons = new ArrayList<>();
        childAxons = new ArrayList<>();
    }

    public Node(IActivationFunctionContainer funcContainer){

        parentAxons = new ArrayList<>();
        childAxons = new ArrayList<>();
        activationFunctionContainer = funcContainer;
    }

    //endregion

    //region -- methods --

    public void addParentAxon(IAxon axon) {
        parentAxons.add(axon);
    }

    public void addChildAxon(IAxon axon) {
        childAxons.add(axon);
    }

    public void setActivationFunctionContainer(IActivationFunctionContainer activationFunctionContainer) {
        this.activationFunctionContainer = activationFunctionContainer;
    }

    public DoubleFunction<Double> getActivationFunction() {
        return activationFunctionContainer.getActivationFunction();
    }

    public ActivationFunction getActivationFunctionType() {
        return activationFunctionContainer.getActivationFunctionType();
    }

    //endregion
}