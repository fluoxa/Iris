package de.baleipzig.iris.model.neuralnet.axon;
import de.baleipzig.iris.model.neuralnet.node.INode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Axon implements IAxon {

    private double weight;
    private INode childNode;
    private INode parentNode;
}