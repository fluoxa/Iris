package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.node.INode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeWorker implements INodeWorker {

    public void calculateActivation(INode node) {

        double result = 0.;

        List<IAxon> parentAxons = node.getParentAxons();

        for(IAxon parentAxon : parentAxons){
            result += parentAxon.getWeight() * parentAxon.getParentNode().getActivation();
        }

        node.setWeightedInput(result + node.getBias());

        node.setActivation(node.getActivationFunctionContainer().getFunction().apply(node.getWeightedInput()));
    }
}