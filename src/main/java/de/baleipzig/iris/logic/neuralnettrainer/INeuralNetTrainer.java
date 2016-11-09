package de.baleipzig.iris.logic.neuralnettrainer;

import de.baleipzig.iris.logic.neuralnettrainer.result.TestResult;
import de.baleipzig.iris.logic.neuralnettrainer.result.Result;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

import java.util.Map;

public interface INeuralNetTrainer<InputType, OutputType> {

    void addTrainingListener(INeuralNetListener listener);
    void removeTrainingListener(INeuralNetListener listener);

    void setNeuralNet(INeuralNet neuralNet);
    INeuralNet getNeuralNet();

    void interrupt();

    Result train (Map<InputType, OutputType> trainingData);

    TestResult getTestResult(Map<InputType, OutputType> testData);
}
