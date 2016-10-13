package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.ActivationFunctions;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.node.INode;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;


public class NodeWorkerTest {

    private INode node = mock(INode.class);
    private INode parentNode1 = mock(INode.class);
    private INode parentNode2 = mock(INode.class);
    private IAxon axon1 = mock(IAxon.class);
    private IAxon axon2 = mock(IAxon.class);

    @Test
    public void calculateState_ReturnsExpectedState() {

        when(node.getBias()).thenReturn(1.5);
        when(node.getActivationFunction()).thenReturn(ActivationFunctions::identity);
        when(node.getParentAxons()).thenReturn(Arrays.asList(axon1, axon2));
        when(parentNode1.getState()).thenReturn(3.4);
        when(parentNode2.getState()).thenReturn(-2.);

        when(axon1.getChildNode()).thenReturn(node);
        when(axon2.getChildNode()).thenReturn(node);
        when(axon1.getParentNode()).thenReturn(parentNode1);
        when(axon2.getParentNode()).thenReturn(parentNode2);
        when(axon1.getWeight()).thenReturn(0.4);
        when(axon2.getWeight()).thenReturn(0.9);

        NodeWorker worker = new NodeWorker();

        worker.calculateState(node);

        verify(node, times(1)).setState();
    }
}