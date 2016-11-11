package de.baleipzig.iris.ui.viewmodel.training;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingViewModel {

    private double learningRate;
    private int miniBadgeSize;
    private int trainingSetSize;
    private int trainingCycles;

    private double overallTrainingProgress;
    private double cycleProgress;

    private INeuralNet neuralNet;
    private UUID selectedNeuralNetId;
}
