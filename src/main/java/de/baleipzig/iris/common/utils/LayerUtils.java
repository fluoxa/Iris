package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.ArrayList;
import java.util.List;

public class LayerUtils {

    public static int getNumberOfNodes(ILayer layer){
        return layer.getDim().getDegreesOfFreedom();
    }

    public static List<INode> getAllNodesLinewise(ILayer layer){

        int sizeX = layer.getDim().getX();
        int sizeY = layer.getDim().getY();
        List<INode> list = new ArrayList<>(sizeX * sizeY);

        for(int y = 0; y < sizeY; y++)
            for(int x = 0; x < sizeX; x++){
                list.add(layer.getNode(x,y));
            }

        return list;
    }
}