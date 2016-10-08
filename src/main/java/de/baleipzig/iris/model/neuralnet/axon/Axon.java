package de.baleipzig.iris.model.neuralnet.axon;
import de.baleipzig.iris.model.neuralnet.node.INode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Axon implements IAxon {

    private double weight;
    private INode childNode;
    private INode parentNode;
}