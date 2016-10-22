package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.enums.NeuralNetCoreType;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetCoreUtils {

    public static int getNumberOfNodes(INeuralNetCore net){

        int number =  net.getInputLayer().getDim().getDegreesOfFreedom();

        for(ILayer layer : net.getHiddenLayers()){
            number += layer.getDim().getDegreesOfFreedom();
        }

        number += net.getOutputLayer().getDim().getDegreesOfFreedom();

        return number;
    }

    public static List<INode> getAllNodesLinewiseBottomToTop(INeuralNetCore net){

        List<INode> list = new ArrayList<>(NeuralNetCoreUtils.getNumberOfNodes(net));

        list.addAll(LayerUtils.getAllNodesLinewise(net.getInputLayer()));

        for(ILayer layer : net.getHiddenLayers()){
            list.addAll(LayerUtils.getAllNodesLinewise(layer));
        }

        list.addAll(LayerUtils.getAllNodesLinewise(net.getOutputLayer()));

        return list;
    }

    public static NeuralNetCoreType getNeuralNetType(INeuralNetCore net){

        INode node = net.getInputLayer().getNode(0,0);
        if(node instanceof Node){
            return NeuralNetCoreType.TRAIN;
        }

        return NeuralNetCoreType.PROD;
    }
}