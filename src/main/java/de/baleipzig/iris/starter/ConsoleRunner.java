package de.baleipzig.iris.starter;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.ActivationFunctions;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNet;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;

public class ConsoleRunner {

    public static void main(String[] args) {

        // noch wird die aktivierungsfunktion direkt im knoten hinterlegt,
        // koennte man auch durch ein enum ersetzen und über ne factory später
        // holen, laesst sich vielleicht leichter persistieren al nen funtionspointer

        // die daten die gespeicher werden muessen
        // Axon: alles
        // Node: aktivierungsfunktion + bias + childAxons + parentAxons
        // Layer: layer
        // NeuralNet: alles

        String sinnloseAenderung;

        DoubleFunction<Double> func = ActivationFunctions::sigmoid;

        INode node1 = new Node(func);
        INode node2 = new Node(func);
        ILayer inputLayer = new Layer();
        inputLayer.addNode(node1);
        inputLayer.addNode(node2);

        INode node3 = new Node(func);
        IAxon axon13 = new Axon();
        axon13.setChildNode(node1);
        axon13.setParentNode(node3);
        axon13.setWeight(2.3);
        node3.addParentAxon(axon13);

        IAxon axon23 = new Axon();
        axon23.setChildNode(node2);
        axon23.setParentNode(node3);
        axon23.setWeight(-1.3);
        node3.addParentAxon(axon23);

        ILayer hiddenLayer = new Layer();
        hiddenLayer.addNode(node3);

        INode node4 = new Node(func);
        IAxon axon34 = new Axon();
        axon34.setChildNode(node3);
        axon34.setParentNode(node4);
        axon34.setWeight(1.3);
        node3.addChildAxon(axon34);
        node4.addParentAxon(axon34);

        ILayer outputLayer = new Layer();
        outputLayer.addNode(node4);

        INeuralNet net = new NeuralNet();
        net.setInputLayer(inputLayer);
        net.addHiddenLayer(hiddenLayer);
        net.setOutputLayer(outputLayer);
    }
}