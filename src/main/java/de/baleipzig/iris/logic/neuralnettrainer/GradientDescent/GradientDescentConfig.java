package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import lombok.Getter;
import lombok.Setter;

public class GradientDescentConfig {

    //region -- member --

    @Getter @Setter
    private double learningRate;

    @Getter @Setter
    private int trainingSetSize;

    @Getter @Setter
    private int trainingCycles;

    @Getter @Setter
    private int badgeSize;

    //endregion

    //region -- methods --

    public boolean isValid() {

        return  Math.abs(learningRate * trainingCycles * trainingSetSize * badgeSize) > 1e-10;
    }

    //endregion
}