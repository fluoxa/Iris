package de.baleipzig.iris.model.neuralnet.node;

import de.baleipzig.iris.model.neuralnet.axon.IAxon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.function.DoubleFunction;

@Data
@AllArgsConstructor
public class Node implements INode {

    //region -- member --

    @Getter
    private ArrayList<IAxon> parentAxons;
    @Getter
    private ArrayList<IAxon> childAxons;

    private double state;
    private double bias;
    private double error;
    private double preActivation;

    private DoubleFunction<Double> activationFunction;

    //endregion

    //region -- constructor --

    public Node(){

        parentAxons = new ArrayList<>();
        childAxons = new ArrayList<>();
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