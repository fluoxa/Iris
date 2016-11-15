package de.baleipzig.iris.persistence.entity.neuralnet;

import de.baleipzig.iris.enums.NeuralNetCoreType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class NeuralNetEntity {

    @Id
    private String neuralNetId = "";

    private String name = "";
    private String description = "";
    private String type = NeuralNetCoreType.TRAIN.toString();

    private NeuralNetCoreEntity neuralNetCoreEntity = new NeuralNetCoreEntity();
}
