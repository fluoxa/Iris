package de.baleipzig.iris.ui.viewmodel.neuralnetconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeuralNetConfigViewModel {

    private String name;
    private String info;
    private String netStructure;
}
