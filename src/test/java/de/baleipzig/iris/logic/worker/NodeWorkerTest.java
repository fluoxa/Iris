package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.model.neuralnet.activationfunction.IdentityFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.node.INode;
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
    public void calculateActivation_ReturnsExpectedActivation() {

        when(node.getBias()).thenReturn(1.5);
        when(node.getActivationFunctionContainer()).thenReturn(new IdentityFunctionContainer());
        when(node.getParentAxons()).thenReturn(Arrays.asList(axon1, axon2));
        when(node.getWeightedInput()).thenReturn(1.06);
        when(parentNode1.getActivation()).thenReturn(3.4);
        when(parentNode2.getActivation()).thenReturn(-2.);

        when(axon1.getChildNode()).thenReturn(node);
        when(axon2.getChildNode()).thenReturn(node);
        when(axon1.getParentNode()).thenReturn(parentNode1);
        when(axon2.getParentNode()).thenReturn(parentNode2);
        when(axon1.getWeight()).thenReturn(0.4);
        when(axon2.getWeight()).thenReturn(0.9);

        NodeWorker worker = new NodeWorker();

        worker.calculateActivation(node);

        verify(node, times(1)).setWeightedInput(1.06);
        verify(node, times(1)).setActivation(1.06);
        verify(node, times(1)).getParentAxons();
        verify(node, times(1)).getBias();
        verify(node, times(1)).getActivationFunctionContainer();
        verify(axon1, times(1)).getWeight();
        verify(axon2, times(1)).getWeight();
        verify(axon1, times(1)).getParentNode();
        verify(axon2, times(1)).getParentNode();
    }
}