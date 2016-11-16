package de.baleipzig.iris.model.neuralnet.neuralnet;

import lombok.Data;

import java.util.UUID;

@Data
public class NeuralNetMetaData implements INeuralNetMetaData {

    private UUID id = UUID.randomUUID();
    private String name = "";
    private String description = "";
}