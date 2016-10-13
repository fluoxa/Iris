package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public interface ILayerWorker {

    void propagateForward(ILayer layer);
}
