package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

public interface IGradientDescentNeuralNetTrainer {

    void propagateBackward(INeuralNet neuralNet, ILayer expectedOutputLayer);
}