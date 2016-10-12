package de.baleipzig.iris.model.neuralnet.neuralnet;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;

import java.util.List;

public interface INeuralNetCore {

    void setInputLayer(ILayer layer);
    ILayer getInputLayer();
    void setOutputLayer(ILayer layer);
    ILayer getOutputLayer();
    void addHiddenLayer(ILayer layer);
    List<ILayer> getHiddenLayers();
}
