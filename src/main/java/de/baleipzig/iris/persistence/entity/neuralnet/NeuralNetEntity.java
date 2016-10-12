package de.baleipzig.iris.persistence.entity.neuralnet;

import de.baleipzig.iris.enums.NeuralNetCoreType;
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
    private Map<Integer, LayerEntity> layers = new HashMap<>();
    private Map<String, AxonEntity> axons = new HashMap<>();
}
