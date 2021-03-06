package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.enums.NeuralNetCoreType;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class NeuralNetCoreUtils {

    private static final Object syncObject = new Object();

    public static int getNumberOfNodes(INeuralNetCore net){

        int number =  net.getInputLayer().getDim().getDegreesOfFreedom();

        for(ILayer layer : net.getHiddenLayers()){
            number += layer.getDim().getDegreesOfFreedom();
        }

        number += net.getOutputLayer().getDim().getDegreesOfFreedom();

        return number;
    }

    public static int getNumberOfParentAxons(INeuralNetCore core) {

        Object[] parameters = new Object[1];
        parameters[0] = 0;

        BiConsumer<INode, Object[]> axonCounter = (node, params) -> {

            synchronized (syncObject) {
                Integer number = (Integer) params[0] + node.getParentAxons().size();
                params[0] = number;
            }
        };

        for(ILayer hLayer : core.getHiddenLayers()){
            hLayer.applyToLayerNodes(axonCounter, parameters);
        }

        core.getOutputLayer().applyToLayerNodes(axonCounter, parameters);

        return (Integer)parameters[0];
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

        if(net.getInputLayer() == null || LayerUtils.getNumberOfNodes(net.getInputLayer()) == 0) {
            return NeuralNetCoreType.UNDEFINED;
        }

        INode node = net.getInputLayer().getNode(0,0);
        if(node instanceof Node){
            return NeuralNetCoreType.TRAIN;
        }

        return NeuralNetCoreType.PROD;
    }
}