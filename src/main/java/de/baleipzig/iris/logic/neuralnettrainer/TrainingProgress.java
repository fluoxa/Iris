package de.baleipzig.iris.logic.neuralnettrainer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainingProgress {

    public double overallProgress;
    public double cycleProgress;

    public void reset() {

        overallProgress = 0.;
        cycleProgress = 0.;
    }
}
