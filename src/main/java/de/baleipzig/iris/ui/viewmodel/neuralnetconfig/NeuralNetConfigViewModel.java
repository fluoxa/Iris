package de.baleipzig.iris.ui.viewmodel.neuralnetconfig;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeuralNetConfigViewModel {

    private INeuralNet neuralNet;
    private UUID selectedNeuralNetId;

    private String name;
    private String description;
    private String netStructure;
    private String originalNetStructure;
}
