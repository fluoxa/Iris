package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

public interface IMiniBadgeNodeTrainer extends IGradientDescentNodeTrainer {
    void updateBiasWeightCache(INeuralNet neuralNet);
}