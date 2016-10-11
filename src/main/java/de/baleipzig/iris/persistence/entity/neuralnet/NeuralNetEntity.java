package de.baleipzig.iris.persistence.entity.neuralnet;

import de.baleipzig.iris.logic.converter.NeuralNetType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.*;

@Data
public class NeuralNetEntity {

    @Id
    private String neuralNetId = UUID.randomUUID().toString();
    private String name = "";
    private String description = "";
    private String type = NeuralNetType.train.toString();
    private Map<Long, NodeEntity> nodes = new HashMap<>();
    private List<Long> inputLayer = new ArrayList<>();
    private List<List<Long>> hiddenLayers = new ArrayList<>();
    private List<Long> outputLayer = new ArrayList<>();
    private Map<String, AxonEntity> axons = new HashMap<>();
}
