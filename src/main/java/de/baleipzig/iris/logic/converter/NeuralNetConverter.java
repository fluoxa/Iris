package de.baleipzig.iris.logic.converter;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNet;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;
import de.baleipzig.iris.persistence.entity.neuralnet.AxonEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NodeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NeuralNetConverter {

    private long currentId;


    public static INeuralNet fromEntity(NeuralNetEntity neuralNetEntity) {
        Map<Long, INode> allNodes = new HashMap<>(neuralNetEntity.getNodes().size());

        createAllNodesFromEntity(neuralNetEntity, allNodes);

        createAxonsAndLinkWithNodes(neuralNetEntity, allNodes);

        ILayer inputLayer = createLayer(neuralNetEntity.getInputLayer(), allNodes);

        List<ILayer> hiddenLayers = new ArrayList<>(neuralNetEntity.getHiddenLayers().size());
        for (List<Long> hiddenLayer : neuralNetEntity.getHiddenLayers()) {
            hiddenLayers.add(createLayer(hiddenLayer, allNodes));
        }

        ILayer outputLayer = createLayer(neuralNetEntity.getOutputLayer(), allNodes);

        INeuralNet net = new NeuralNet();
        net.setInputLayer(inputLayer);

        //Todo: unit-test auf richtige reihenfolge der Layer
        for (ILayer layer : hiddenLayers) {
            net.addHiddenLayer(layer);
        }

        net.setOutputLayer(outputLayer);

        return net;
    }

    private static void createAxonsAndLinkWithNodes(NeuralNetEntity neuralNetEntity, Map<Long, INode> allNodes) {
        neuralNetEntity.getAxons().forEach((key, axonEntity) -> {
            String[] parentAndChild = key.split(AxonEntity.PARENT_TO_CHILD_DELIMITER);

            INode parentNode = allNodes.get(Long.valueOf(parentAndChild[0]));
            INode childNode = allNodes.get(Long.valueOf(parentAndChild[1]));

            IAxon axon = new Axon();
            axon.setParentNode(parentNode);
            axon.setChildNode(childNode);
            axon.setWeight(axonEntity.getWeight());

            parentNode.addChildAxon(axon);
            childNode.addParentAxon(axon);
        });
    }

    private static void createAllNodesFromEntity(NeuralNetEntity neuralNetEntity, Map<Long, INode> allNodes) {
        neuralNetEntity.getNodes().forEach((nodeId, nodeEntity) -> {
            INode node = new Node(); //todo factory netztyp
            node.setBias(nodeEntity.getBias());
            //todo function factory befragen
            allNodes.put(nodeId, node);
        });
    }

    ;

    private static ILayer createLayer(List<Long> ids, Map<Long, INode> allNodes) {
        ILayer layer = new Layer();
        for (Long id : ids) {
            layer.addNode(allNodes.get(id));
        }

        return layer;
    }

    public static NeuralNetEntity toEntity(INeuralNet neuralNetFromModell) {

        NeuralNetEntity neuralNetEntity = new NeuralNetEntity();

        Map<INode, Long> allNodesFromModel = new HashMap<>();

        long currentNodeId = 0L;

        ILayer inputLayerFromModel = neuralNetFromModell.getInputLayer();
        createNodeEntitiesAndAddToLayer(neuralNetFromModell,
                inputLayerFromModel,
                allNodesFromModel,
                neuralNetEntity,
                neuralNetEntity.getInputLayer(),
                currentNodeId);

        for (ILayer hiddenLayer : neuralNetFromModell.getHiddenLayer()) {
            List<Long> hiddenLayerEntity = new ArrayList<>();
            createNodeEntitiesAndAddToLayer(neuralNetFromModell,
                    hiddenLayer,
                    allNodesFromModel,
                    neuralNetEntity,
                    hiddenLayerEntity,
                    currentNodeId);

            neuralNetEntity.getHiddenLayers().add(hiddenLayerEntity);
        }

        ILayer outputLayerFromModel = neuralNetFromModell.getOutputLayer();
        createNodeEntitiesAndAddToLayer(neuralNetFromModell,
                inputLayerFromModel,
                allNodesFromModel,
                neuralNetEntity,
                neuralNetEntity.getInputLayer(),
                currentNodeId);

        allNodesFromModel.forEach((node, id) -> {

            node.getParentAxons().forEach(axon -> {
                long parentNodeId = allNodesFromModel.get(axon.getParentNode());
                long childNodeId = allNodesFromModel.get(axon.getChildNode());
                double weight = axon.getWeight();

                AxonEntity axonEntity = new AxonEntity();
                axonEntity.setParentNodeId(parentNodeId);
                axonEntity.setChildNodeId(childNodeId);
                axonEntity.setWeight(axon.getWeight());
            });

        });


        return null;
    }

    private static void createNodeEntitiesAndAddToLayer(INeuralNet neuralNetFromModel,
                                                        ILayer layerFromModel,
                                                        Map<INode, Long> allNodesFromModel,
                                                        NeuralNetEntity neuralNetEntity,
                                                        List<Long> layerEntity,
                                                        long currentNodeId) {

        Dimension dimension = layerFromModel.getDim();
        for (int y = 0; y < dimension.getY(); y++) {
            for (int x = 0; x < dimension.getX(); x++) {
                INode node = layerFromModel.getNode(x, y);
                allNodesFromModel.put(node, currentNodeId);

                NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setNodeId(currentNodeId);
                nodeEntity.setBias(node.getBias());
                nodeEntity.getActivationFunctionName();
                neuralNetEntity.getNodes().put(currentNodeId, nodeEntity);

                neuralNetEntity.getInputLayer().add(currentNodeId);

                currentNodeId++;
            }
        }
    }

    ;

}
