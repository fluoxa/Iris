package de.baleipzig.iris.persistence.entity.neuralnet;

import de.baleipzig.iris.logic.converter.NeuralNetCoreType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.*;

@Data
public class NeuralNetEntity {

    @Id
    private String neuralNetId = "";
    private String name = "";
    private String description = "";
    private String type = NeuralNetCoreType.train.toString();
    private Map<Long, NodeEntity> nodes = new HashMap<>();
    private List<Long> inputLayer = new ArrayList<>();
    private List<List<Long>> hiddenLayers = new ArrayList<>();
    private List<Long> outputLayer = new ArrayList<>();
    private Map<String, AxonEntity> axons = new HashMap<>();
}
