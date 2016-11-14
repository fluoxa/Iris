package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.activationfunction.GenericFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetCore;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.function.DoubleFunction;

import static org.mockito.Mockito.*;

public class NeuralNetWorkerTest {

    private ILayerWorker layerWorker = mock(ILayerWorker.class);
    private INeuralNetEntityRepository repo = mock(INeuralNetEntityRepository.class);
    private INeuralNetCore netCore = mock(INeuralNetCore.class);
    private INeuralNet net = mock(INeuralNet.class);
    private ILayer inputLayer = mock(ILayer.class);
    private ILayer hiddenLayer1 = mock(ILayer.class);
    private ILayer hiddenLayer2 = mock(ILayer.class);
    private ILayer outputLayer = mock(ILayer.class);

    @Test
    public void propagateForward_ReturnsTrue_WhenLayerWorkerCalled3Times() {

        when(net.getNeuralNetCore()).thenReturn(netCore);
        when(netCore.getHiddenLayers()).thenReturn(Arrays.asList(hiddenLayer1, hiddenLayer2));
        when(netCore.getOutputLayer()).thenReturn(outputLayer);
        when(netCore.getInputLayer()).thenReturn(inputLayer);

        INeuralNetWorker worker = new NeuralNetWorker(repo, layerWorker,null);

        worker.propagateForward(net);

        verify(net, times(1)).getNeuralNetCore();
        verify(layerWorker, times(3)).propagateForward(any(ILayer.class));
    }

    @DataProvider(name = "xorNet_InputOutput")
    public static Object[][] applyToLayerNodes_data() {
        return new Object[][]{
                {0., 0., 0.},
                {1., 0., 1.},
                {0., 1., 1.},
                {1., 1., 0.}
        };
    }

    @Test(dataProvider = "xorNet_InputOutput")
    public void propagateForward_ReturnsExpectedResult_ForXorNet(double input1, double input2, double result) {

        INeuralNet net = setupXorNet();
        ILayer iLayer = net.getNeuralNetCore().getInputLayer();
        iLayer.getNode(0,0).setActivation(input1);
        iLayer.getNode(1,0).setActivation(input2);

        INeuralNetWorker worker = new NeuralNetWorker(repo, new LayerWorker(new NodeWorker()), null);

        worker.propagateForward(net);

        Assert.assertEquals(net.getNeuralNetCore().getOutputLayer().getNode(0,0).getActivation(), result);
    }

    private static INeuralNet setupXorNet(){

        INeuralNet net = new NeuralNet();
        INeuralNetCore netCore = new NeuralNetCore();

        INode node1 = new Node();
        INode node2 = new Node();

        ILayer inputLayer = new Layer();
        inputLayer.resize(new Dimension(2,1));
        inputLayer.addNode(node1);
        inputLayer.addNode(node2);

        DoubleFunction<Double> maxFunc = x ->  Math.max(0., x);

        INode node3 = new Node();
        node3.setActivationFunctionContainer(new GenericFunctionContainer(maxFunc));
        node3.setBias(0.);
        IAxon link13 = new Axon();
        link13.setParentNode(node1);
        link13.setChildNode(node3);
        link13.setWeight(1.);
        IAxon link23 = new Axon();
        link23.setParentNode(node2);
        link23.setChildNode(node3);
        link23.setWeight(1.);
        node3.addParentAxon(link13);
        node3.addParentAxon(link23);

        INode node4 = new Node();
        node4.setActivationFunctionContainer(new GenericFunctionContainer(maxFunc));
        node4.setBias(-1.);
        IAxon link14 = new Axon();
        link14.setParentNode(node1);
        link14.setChildNode(node4);
        link14.setWeight(1.);
        IAxon link24 = new Axon();
        link24.setParentNode(node2);
        link24.setChildNode(node4);
        link24.setWeight(1.);
        node4.addParentAxon(link13);
        node4.addParentAxon(link23);

        ILayer hiddenLayer = new Layer();
        hiddenLayer.resize(new Dimension(2,1));
        hiddenLayer.addNode(node3);
        hiddenLayer.addNode(node4);

        INode node5 = new Node();
        node5.setActivationFunctionContainer(new GenericFunctionContainer(maxFunc));
        node5.setBias(0.);
        IAxon link35 = new Axon();
        link35.setParentNode(node3);
        link35.setChildNode(node5);
        link35.setWeight(1.);
        IAxon link45 = new Axon();
        link45.setParentNode(node4);
        link45.setChildNode(node5);
        link45.setWeight(-2.);
        node5.addParentAxon(link35);
        node5.addParentAxon(link45);

        ILayer outputLayer = new Layer();
        outputLayer.resize(new Dimension(1,1));
        outputLayer.addNode(node5);

        netCore.setInputLayer(inputLayer);
        netCore.addHiddenLayer(hiddenLayer);
        netCore.setOutputLayer(outputLayer);

        net.setNeuralNetCore(netCore);
        return net;
    }
}