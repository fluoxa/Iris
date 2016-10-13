package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.List;

public class NodeWorker implements INodeWorker {

    public void calculateState(INode node) {

        double result = 0.;

        List<IAxon> parentAxons = node.getParentAxons();

        for(IAxon parentAxon : parentAxons){
            result += parentAxon.getWeight() * parentAxon.getParentNode().getState();
        }

        node.setPreActivation(result + node.getBias());

        node.setState(node.getActivationFunction().apply(node.getPreActivation()));
    }
}