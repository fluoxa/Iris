package de.baleipzig.iris.logic;


import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

public interface INeuralNetWorker {

    void save(INeuralNet neuralNet);

    INeuralNet load(String neuralNetId);

    void delete(String neuralNetId);
}
