package de.baleipzig.iris.ui.viewmodel.training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingViewModel {
    private double learningRate;
    private int miniBadgeSize;
    private int trainingSetSize;
    private int trainingCycles;
}
