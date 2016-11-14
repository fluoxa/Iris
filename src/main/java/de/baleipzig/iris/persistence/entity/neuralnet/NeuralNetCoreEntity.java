package de.baleipzig.iris.persistence.entity.neuralnet;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NeuralNetCoreEntity {

    private Map<Long, NodeEntity> nodes = new HashMap<>();
    private Map<Integer, LayerEntity> layers = new HashMap<>();
    private Map<String, AxonEntity> axons = new HashMap<>();
}
