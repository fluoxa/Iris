package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.node.INode;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NeuralNetUtilsIntegrationTest {

    private INeuralNet net = mock(INeuralNet.class);
    private INeuralNetCore core = mock(INeuralNetCore.class);
    private INode node0 = mock(INode.class);
    private INode node1 = mock(INode.class);
    private INode node2 = mock(INode.class);
    private INode node3 = mock(INode.class);
    private ILayer iLayer = new Layer();
    private ILayer oLayer = new Layer();
    private ILayer hLayer = new Layer();

    @BeforeTest
    public void Setup() {

        when(net.getNeuralNetCore()).thenReturn(core);
        when(core.getHiddenLayers()).thenReturn(Arrays.asList(hLayer));
        when(core.getInputLayer()).thenReturn(iLayer);
        when(core.getOutputLayer()).thenReturn(oLayer);
        iLayer.resize(new Dimension(1,1));
        iLayer.addNode(node0);
        hLayer.resize(new Dimension(2,1));
        hLayer.addNode(node1);
        hLayer.addNode(node2);
        oLayer.resize(new Dimension(1,1));
        oLayer.addNode(node3);
    }

    @Test
    public void getNumberOfParentAxons_ReturnsCorrectNumberOfParentAxons() {

        when(node0.getParentAxons()).thenReturn(new ArrayList<>());
        when(node1.getParentAxons()).thenReturn(Arrays.asList(mock(IAxon.class)));
        when(node2.getParentAxons()).thenReturn(Arrays.asList(mock(IAxon.class)));
        when(node3.getParentAxons()).thenReturn(Arrays.asList(mock(IAxon.class), mock(IAxon.class)));

        long result = NeuralNetCoreUtils.getNumberOfParentAxons(net.getNeuralNetCore());

        Assert.assertEquals(result, 4);
    }
}