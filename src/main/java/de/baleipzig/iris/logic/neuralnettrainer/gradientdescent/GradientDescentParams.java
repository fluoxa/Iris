package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import lombok.Getter;

@Getter
public class GradientDescentParams {

    //region -- member --

    private final double learningRate;
    private final int trainingSetSize;
    private final int trainingCycles;
    private final int badgeSize;

    //endregion

    //region -- Constructor --

    public GradientDescentParams(double learningRate,
                                 int    trainingSetSize,
                                 int    trainingCycles,
                                 int    badgeSize) {

        this.learningRate = learningRate;
        this.trainingSetSize = trainingSetSize;
        this.trainingCycles = trainingCycles;
        this.badgeSize = badgeSize;

        if( Math.abs(learningRate * trainingCycles * trainingSetSize * badgeSize) < 1e-10 ) {
            throw new RuntimeException("invalid GradientDescentParams");
        }
    }

    //endregion
}