package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LayerWorker implements ILayerWorker{

    private final INodeWorker nodeWorker;

    public void propagateForward(ILayer layer){
        layer.applyToLayerNodes(nodeWorker::calculateActivation);
    }
}