package de.baleipzig.iris.persistence.subset;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class NeuralNetSubset {

    @Id
    private String neuralNetId = "";
    private String name = "";
    private String description = "";
}
