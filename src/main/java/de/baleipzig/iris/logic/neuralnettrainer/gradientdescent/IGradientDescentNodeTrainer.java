package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.model.neuralnet.node.INode;

public interface IGradientDescentNodeTrainer {

    void propagateOutputNodeBackward(INode outputNode, INode expectedNode);
    void propagateBackward(INode node);
}