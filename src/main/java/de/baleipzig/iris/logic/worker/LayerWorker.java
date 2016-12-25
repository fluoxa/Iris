package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LayerWorker implements ILayerWorker{

    private final INodeWorker nodeWorker;

    public void propagateForward(ILayer layer){
        layer.applyToLayerNodes(nodeWorker::calculateActivation);
    }

    public void normalizeLayerActivations(ILayer layer){

        double totalActivation = 0;
        final Object sync = new Object();

        BiConsumer<INode, Object[]> getTotalActivation = (node, totActivation) -> {
            synchronized (sync) {
                totActivation[0] = (double)(totActivation[0]) + node.getActivation();
            }
        };

        Object[] params = new Object[] {totalActivation};

        layer.applyToLayerNodes(getTotalActivation, params);

        BiConsumer<INode, Object[]> normalize = (node, param )-> node.setActivation(node.getActivation()/(double)(param[0]));

        layer.applyToLayerNodes(normalize, params);
    }
}