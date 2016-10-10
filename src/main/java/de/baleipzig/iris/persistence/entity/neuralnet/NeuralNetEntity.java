package de.baleipzig.iris.persistence.entity.neuralnet;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class NeuralNetEntity {

    @Id
    private String neuralNetId = UUID.randomUUID().toString();

    private String name;

    private Map<Long, NodeEntity> nodes = new HashMap<>();

    private Map<String, AxonEntity> axons = new HashMap<>();
}
