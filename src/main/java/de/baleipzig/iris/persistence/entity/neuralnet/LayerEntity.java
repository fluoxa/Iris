package de.baleipzig.iris.persistence.entity.neuralnet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LayerEntity {

    private int dimX = 0;
    private int dimY = 0;
    private List<Long> nodes = new ArrayList<>();
}