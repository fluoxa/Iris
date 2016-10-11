package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.logic.converter.NeuralNetType;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetUtils {

    public static int getNumberOfNodes(INeuralNet net){

        int number =  net.getInputLayer().getDim().getDegreesOfFreedom();

        for(ILayer layer : net.getHiddenLayers()){
            number += layer.getDim().getDegreesOfFreedom();
        }

        number += net.getOutputLayer().getDim().getDegreesOfFreedom();

        return number;
    }

    public static List<INode> getAllNodesLinewiseBottomToTop(INeuralNet net){

        List<INode> list = new ArrayList<>(NeuralNetUtils.getNumberOfNodes(net));

        list.addAll(LayerUtils.getAllNodesLinewise(net.getInputLayer()));

        for(ILayer layer : net.getHiddenLayers()){
            list.addAll(LayerUtils.getAllNodesLinewise(layer));
        }

        list.addAll(LayerUtils.getAllNodesLinewise(net.getOutputLayer()));

        return list;
    }

    public static NeuralNetType getNeuralNetType(INeuralNet net){

        INode node = net.getInputLayer().getNode(0,0);
        if(node instanceof Node){
            return NeuralNetType.train;
        }

        return NeuralNetType.prod;
    }
}