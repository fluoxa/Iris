package de.baleipzig.iris.model.neuralnet.node;

import de.baleipzig.iris.model.neuralnet.activationfunction.IFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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

    @Getter
    @Setter
    private IFunctionContainer activationFunctionContainer;

    //endregion

    //region -- constructor --

    public Node(){

        parentAxons = new ArrayList<>();
        childAxons = new ArrayList<>();
    }

    public Node(IFunctionContainer funcContainer){

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

    //endregion
}