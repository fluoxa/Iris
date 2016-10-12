package de.baleipzig.iris.model.neuralnet.neuralnet;

import java.util.UUID;

public interface INeuralNetMetaData {

    UUID getId();
    void setId(UUID id);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);
}
