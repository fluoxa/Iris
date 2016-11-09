package de.baleipzig.iris.logic.neuralnettrainer;

public interface INeuralNetListener {

    void receiveTrainingProgress(double overallProgress, double cycleProgress);
}
