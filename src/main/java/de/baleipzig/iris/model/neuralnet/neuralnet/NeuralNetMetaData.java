package de.baleipzig.iris.model.neuralnet.neuralnet;

import lombok.Data;

import java.util.UUID;

@Data
public class NeuralNetMetaData implements INeuralNetMetaData {

    private UUID id;
    private String name;
    private String description;

    public NeuralNetMetaData(){
        id = UUID.randomUUID();
    }
}