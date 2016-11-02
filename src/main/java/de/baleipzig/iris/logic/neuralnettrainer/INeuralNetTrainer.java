package de.baleipzig.iris.logic.neuralnettrainer;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import java.util.Map;

public interface INeuralNetTrainer<InputType, OutputType> {

    void setNeuralNet(INeuralNet neuralNet);
    INeuralNet getNeuralNet();

    void interruptTraining();

    void train (Map<InputType, OutputType> trainingData);
}
