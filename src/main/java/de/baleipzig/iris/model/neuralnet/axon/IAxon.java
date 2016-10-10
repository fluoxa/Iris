package de.baleipzig.iris.model.neuralnet.axon;

import de.baleipzig.iris.model.neuralnet.node.INode;

public interface IAxon {

    void setWeight(double weight);
    double getWeight();

    INode getChildNode();
    void setChildNode(INode node);

    INode getParentNode();
    void setParentNode(INode node);
}
