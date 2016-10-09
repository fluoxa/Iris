package de.baleipzig.iris.persistence.entity.neuralnet;

import lombok.Data;

@Data
public class AxonEntity {

    private double weight;
    private Long parentNode;
    private Long childNode;

}
