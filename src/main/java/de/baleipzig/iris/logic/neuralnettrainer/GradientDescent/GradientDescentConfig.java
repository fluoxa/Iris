package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradientDescentConfig {

    //region -- member --

    private double learningRate;
    private int trainingSetSize;
    private int trainingCycles;
    private int badgeSize;

    //endregion

    //region -- methods --

    public boolean isValid() {

        return  Math.abs(learningRate * trainingCycles * trainingSetSize * badgeSize) > 1e-10;
    }

    //endregion
}