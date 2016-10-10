package de.baleipzig.iris.model.neuralnet.node;

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
    private double state;

    @Getter
    @Setter
    private double bias;

    @Getter
    @Setter
    private double error;

    @Getter
    @Setter
    private double preActivation;

    private IActivationFunctionContainer activationFunctionContainer;

    private DoubleFunction<Double> activationFunction;

    public Node(){

        parentAxons = new ArrayList<>();
        childAxons = new ArrayList<>();
    }

    public Node(DoubleFunction<Double> func){

        parentAxons = new ArrayList<>();
        childAxons = new ArrayList<>();
        activationFunction = func;
    }

    public void setActivationFunctionContainer(IActivationFunctionContainer activationFunctionContainer) {
        this.activationFunctionContainer = activationFunctionContainer;
    }


    //endregion

    //region -- constructor --

    public DoubleFunction<Double> getActivationFunction() {
        return activationFunctionContainer.getActivationFunction();
    }

    public String getActivationFunctionName() {
        return activationFunctionContainer.getActivationFunctionName();
    }

    //endregion

    //region -- methods --

    public void addParentAxon(IAxon axon) {
        parentAxons.add(axon);
    }

    public void addChildAxon(IAxon axon) {
        childAxons.add(axon);
    }

    //endregion
}