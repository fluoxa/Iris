package de.baleipzig.iris.logic;


import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

public interface INeuralNetWorker {

    public void save(INeuralNet neuralNet);

    public INeuralNet load(String neuralNetId);

    public void delete(String neuralNetId);
}
