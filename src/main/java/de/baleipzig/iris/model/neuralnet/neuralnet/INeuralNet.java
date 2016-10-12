package de.baleipzig.iris.model.neuralnet.neuralnet;

public interface INeuralNet {

    INeuralNetCore getNeuralNetCore();
    void setNeuralNetCore(INeuralNetCore neuralNetCore);

    INeuralNetMetaData getNeuralNetMetaData();
    void setNeuralNetMetaData(INeuralNetMetaData neuralNetCore);

}