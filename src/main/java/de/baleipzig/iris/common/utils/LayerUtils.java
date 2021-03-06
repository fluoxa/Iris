package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.activationfunction.IFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO: Umbauen als Singelton und DI des ApplicationContext zum Anlegen der Korrekten Knoten
public class LayerUtils {

    private static final Random gaussDistribution = new Random();

    public static int getNumberOfNodes(ILayer layer){
        return layer.getDim().getDegreesOfFreedom();
    }

    public static List<INode> getAllNodesLinewise(ILayer layer){

        int sizeX = layer.getDim().getX();
        int sizeY = layer.getDim().getY();
        List<INode> list = new ArrayList<>(sizeX * sizeY);

        for(int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                list.add(layer.getNode(x, y));
            }
        }

        return list;
    }

    public static void copyActivationFromTo(ILayer sourceLayer, ILayer copyLayer) {

        final Dimension sourceDim = sourceLayer.getDim();
        final Dimension copyDim = copyLayer.getDim();

        if( copyDim.getY() != sourceDim.getY() || copyDim.getX() != sourceDim.getX()) {
            throw new RuntimeException("copyActivationFromTo: layer dimensions not matching");
        }

        for(int y = 0; y < sourceDim.getY(); y++) {
            for (int x = 0; x < sourceDim.getX(); x++) {
                copyLayer.getNode(x,y).setActivation(sourceLayer.getNode(x,y).getActivation());
            }
        }
    }

    public static ILayer createLayerWithOptionalRandomBias(Dimension dim, IFunctionContainer activationFunction, boolean useRandomBias){

        ILayer layer = new Layer();
        layer.resize(dim);

        for(int y = 0; y < dim.getY(); y++) {
            for (int x = 0; x < dim.getX(); x++) {

                INode node = new Node();
                node.setActivationFunctionContainer(activationFunction);
                node.setBias(useRandomBias ? gaussDistribution.nextGaussian() : 0.);
                layer.addNode(node);
            }
        }

        return layer;
    }

    public static void fullyConnectLayers(ILayer parentLayer, ILayer childLayer, boolean useRandomWeights){

        int sizeX = parentLayer.getDim().getX();
        int sizeY = parentLayer.getDim().getY();

        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                INode parentNode = parentLayer.getNode(x,y);
                LayerUtils.connectNodeChildLayer(parentNode, childLayer, useRandomWeights);
            }
        }
    }

    private static void connectNodeChildLayer (INode parentNode, ILayer childLayer, boolean useRandomWeights){

        int sizeX = childLayer.getDim().getX();
        int sizeY = childLayer.getDim().getY();

        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){

                INode childNode = childLayer.getNode(x,y);
                IAxon axon = new Axon();
                axon.setWeight(useRandomWeights ? gaussDistribution.nextGaussian() : 0);
                axon.setParentNode(parentNode);
                axon.setChildNode(childNode);
                if(parentNode != null) {
                    parentNode.addChildAxon(axon);
                }
                if(childNode != null) {
                    childNode.addParentAxon(axon);
                }
            }
        }
    }
}