package de.baleipzig.iris.logic.worker;


import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

import java.util.UUID;

public interface INeuralNetWorker extends ICrudWorker<INeuralNet, UUID> {
    void propagateForward(INeuralNet neuralNet);
}
