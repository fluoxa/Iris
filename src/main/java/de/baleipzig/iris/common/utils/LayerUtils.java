package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

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

    public static ILayer createRandomLayer(Dimension dim, boolean useRandomBias){

        ILayer layer = new Layer();
        layer.resize(dim);

        for(int i = 0; i < dim.getY(); i++)
            for (int j = 0; j < dim.getX(); j++){

                INode node = new Node();
                node.setBias( useRandomBias ? (Math.random()-0.5)*10. : 0.); //todo: range herausfinden
                layer.addNode(node);
            }

        return layer;
    }

    public static void fullyConnectLayers(ILayer parent, ILayer child, boolean useRandomWeights){

        int sizeX = parent.getDim().getX();
        int sizeY = parent.getDim().getY();

        for(int i = 0; i < sizeY; i++)
            for(int j = 0; j < sizeX; j++){

                INode tmp = parent.getNode(j,i);
                LayerUtils.connectNodeChildLayer(tmp, child, useRandomWeights);
            }
    }

    private static void connectNodeChildLayer (INode node, ILayer childLayer, boolean useRandomWeights){

        int sizeX = childLayer.getDim().getX();
        int sizeY = childLayer.getDim().getY();

        for(int i = 0; i < sizeY; i++)
            for(int j = 0; j < sizeX; j++){

                INode tmp = childLayer.getNode(j,i);
                IAxon axon = new Axon();
                axon.setWeight(useRandomWeights ? Math.random()-0.5 : 0); //todo: range herausfinden
                axon.setParentNode(node);
                axon.setChildNode(tmp);
                node.addChildAxon(axon);
                tmp.addParentAxon(axon);
            }
    }
}