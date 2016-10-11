package de.baleipzig.iris.logic.converter;

import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.common.utils.NeuralNetUtils;
import de.baleipzig.iris.model.neuralnet.activationfunction.ActivationFunction;
import de.baleipzig.iris.model.neuralnet.activationfunction.ActivationFunctionContainerFactory;
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
import java.util.stream.Collectors;

public class NeuralNetConverter {

    public static INeuralNet fromEntity(NeuralNetEntity neuralNetEntity) {
        Map<Long, INode> allNodes = new HashMap<>(neuralNetEntity.getNodes().size());

        createAllNodesFromEntity(neuralNetEntity, allNodes);

        createAxonsAndLinkWithNodes(neuralNetEntity, allNodes);

        ILayer inputLayer = createLayer(neuralNetEntity.getInputLayer(), allNodes);

        List<ILayer> hiddenLayers = new ArrayList<>(neuralNetEntity.getHiddenLayers().size());
        hiddenLayers.addAll(neuralNetEntity.getHiddenLayers().stream().map(hiddenLayer -> createLayer(hiddenLayer, allNodes)).collect(Collectors.toList()));

        ILayer outputLayer = createLayer(neuralNetEntity.getOutputLayer(), allNodes);

        INeuralNet net = new NeuralNet();
        net.setInputLayer(inputLayer);

        //Todo: unit-test auf richtige reihenfolge der Layer
        hiddenLayers.forEach(net::addHiddenLayer);

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

            if(neuralNetEntity.getType().equals(NeuralNetType.train.toString())){
                parentNode.addChildAxon(axon);
            }
            childNode.addParentAxon(axon);
        });
    }

    private static void createAllNodesFromEntity(NeuralNetEntity neuralNetEntity, Map<Long, INode> allNodes) {
        neuralNetEntity.getNodes().forEach((nodeId, nodeEntity) -> {
            INode node = new Node(); //todo factory netztyp
            node.setBias(nodeEntity.getBias());
            node.setActivationFunctionContainer(ActivationFunctionContainerFactory.getContainer(ActivationFunction.valueOf(nodeEntity.getActivationFunctionType())));
            allNodes.put(nodeId, node);
        });
    }

    private static ILayer createLayer(List<Long> ids, Map<Long, INode> allNodes) {
        ILayer layer = new Layer();
        for (Long id : ids) {
            layer.addNode(allNodes.get(id));
        }

        return layer;
    }

    public static NeuralNetEntity toEntity(INeuralNet neuralNet) {

        int totalNumberOfNodes = NeuralNetUtils.getNumberOfNodes(neuralNet);

        Map<INode, Long> nodeIdMapper = getOrderedNodeIdMap(neuralNet);

        List<INode> orderedNodes = NeuralNetUtils.getAllNodesLinewiseBottomToTop(neuralNet);

        Map<Long, NodeEntity> idNodeEntityMapper = new HashMap<>(totalNumberOfNodes);
        for(INode node : orderedNodes){
            idNodeEntityMapper.put(nodeIdMapper.get(node), toEntity(node, nodeIdMapper));
        }

        List<Long> inputLayerEntity = toLayerEntity(neuralNet.getInputLayer(), nodeIdMapper);
        List<List<Long>> hiddenLayersEntity = new ArrayList<>(neuralNet.getHiddenLayers().size());
        hiddenLayersEntity.addAll(neuralNet.getHiddenLayers().stream().map(layer -> toLayerEntity(layer, nodeIdMapper)).collect(Collectors.toList()));
        List<Long> outputLayerEntity = toLayerEntity(neuralNet.getOutputLayer(), nodeIdMapper);

        Map<String,AxonEntity> axonEntities = getAxonEntities(orderedNodes, nodeIdMapper);

        NeuralNetEntity neuralNetEntity = new NeuralNetEntity();

        neuralNetEntity.setType(NeuralNetUtils.getNeuralNetType(neuralNet).toString());
        neuralNetEntity.setNodes(idNodeEntityMapper);
        neuralNetEntity.setInputLayer(inputLayerEntity);
        neuralNetEntity.setHiddenLayers(hiddenLayersEntity);
        neuralNetEntity.setOutputLayer(outputLayerEntity);
        neuralNetEntity.setAxons(axonEntities);

        return neuralNetEntity;
    }

    private static List<Long> toLayerEntity(ILayer layer, Map<INode, Long> nodeIdMapper) {
        List<INode> orderedInputLayerNodes = LayerUtils.getAllNodesLinewise(layer);
        List<Long> layerEntity = new ArrayList<>(orderedInputLayerNodes.size());
        layerEntity.addAll(orderedInputLayerNodes.stream().map(nodeIdMapper::get).collect(Collectors.toList()));

        return layerEntity;
    }

    private static NodeEntity toEntity(INode node, Map<INode, Long> idMapper){

        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setNodeId(idMapper.get(node));
        nodeEntity.setBias(node.getBias());
        nodeEntity.setActivationFunctionType(node.getActivationFunctionType().toString());

        return nodeEntity;
    }

    private static Map<INode, Long> getOrderedNodeIdMap(INeuralNet net){

        Map<INode, Long> returnMap = new HashMap<>(NeuralNetUtils.getNumberOfNodes(net));

        long index = 0;

        for(INode node : NeuralNetUtils.getAllNodesLinewiseBottomToTop(net)){
            returnMap.put(node, index++);
        }

        return returnMap;
    }

    private static Map<String, AxonEntity> getAxonEntities(List<INode> orderedNodes, Map<INode, Long> nodeIdMapper) {

        Map<String, AxonEntity> axonEntities = new HashMap<>(getNumberOfParentAxons(orderedNodes));

        for(INode node : orderedNodes){
            for(IAxon axon : node.getParentAxons()){

                INode child = axon.getChildNode();
                INode parent = axon.getParentNode();

                AxonEntity axonEntity = new AxonEntity();
                axonEntity.setWeight(axon.getWeight());
                axonEntity.setChildNodeId(nodeIdMapper.get(child));
                axonEntity.setParentNodeId(nodeIdMapper.get(parent));

                String key = String.format("%1d%2s%3d",nodeIdMapper.get(parent),AxonEntity.PARENT_TO_CHILD_DELIMITER, nodeIdMapper.get(child));
                axonEntities.put(key, axonEntity);
            }
        }

        return axonEntities;
    }

    private static int getNumberOfParentAxons(List<INode> orderedNodes){

        int number = 0;

        for(INode node : orderedNodes){
            number += node.getParentAxons().size();
        }

        return number;
    }
}
