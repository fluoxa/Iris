package de.baleipzig.iris.persistence.entity.neuralnet;


import lombok.Data;

@Data
public class NodeEntity {

    private long nodeId;

    private double bias;

    private String activationFunctionType;
}
