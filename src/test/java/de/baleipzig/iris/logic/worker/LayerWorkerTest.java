package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class LayerWorkerTest {

    private INodeWorker nodeWorker = mock(INodeWorker.class);
    private ILayer layer = mock(ILayer.class);

    @Test
    public void propagateForward_ReturnsTrue_WhenApplyToLayerNodesCalledOnce(){

        ILayerWorker worker = new LayerWorker(nodeWorker);

        worker.propagateForward(layer);

        verify(layer, times(1)).applyToLayerNodes(any());
    }
}