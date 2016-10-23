package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public interface IGradientDescentLayerTrainer {

    void propagateOutputLayerBackward(ILayer outputLayer, ILayer expectedResultLayer);
    void propagateBackward(ILayer layer);
}