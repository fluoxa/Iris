package de.baleipzig.iris.starter;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

import java.util.Vector;
import java.util.function.Consumer;

public class ConsoleRunner {

    public static void main(String[] args) {

        INode node1 = new Node();
        node1.setState(12);
        INode node2 = new Node();
        node2.setState(9);

        ILayer layer = new Layer();
        layer.resize(new Dimension(2,2));
        layer.addNode(node1);
        layer.addNode(node2);

        Consumer<INode> func = (node) -> node.setState(node.getState()*2);

        layer.applyToLayerNodes(func);

        System.out.println(node1.getState());
        System.out.println(layer.getNode(0,0).getState());
        System.out.println(node2.getState());

    }
}