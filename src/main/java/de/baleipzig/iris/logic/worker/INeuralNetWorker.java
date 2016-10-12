package de.baleipzig.iris.logic.worker;


import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

import java.util.UUID;

public interface INeuralNetWorker {

    void save(INeuralNet neuralNet);

    INeuralNet load(UUID neuralNetId);

    void delete(UUID neuralNetId);
}
