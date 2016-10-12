package de.baleipzig.iris.model.neuralnet.neuralnet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NeuralNet implements INeuralNet{

    private INeuralNetCore neuralNetCore;
    private INeuralNetMetaData neuralNetMetaData;
}