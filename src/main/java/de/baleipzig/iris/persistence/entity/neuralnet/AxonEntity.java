package de.baleipzig.iris.persistence.entity.neuralnet;

import lombok.Data;

@Data
public class AxonEntity {

    public static final String PARENT_TO_CHILD_DELIMITER = "-";

    private long parentNodeId;

    private long childNodeId;

    private double weight;


}
