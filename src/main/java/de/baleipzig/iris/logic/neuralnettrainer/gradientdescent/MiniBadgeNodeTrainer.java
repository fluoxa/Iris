package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.common.utils.NeuralNetCoreUtils;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleFunction;

public class MiniBadgeNodeTrainer implements IMiniBadgeNodeTrainer {

    private GradientDescentParams config = null;
    private Map<INode, Double> nodePrevBiasesMapper = null;
    private Map<IAxon, Double> axonPrevWeightsMapper = null;

    private int currentBadgeSize = 0;
    private final Object synchronizer = new Object();

    //region -- constructor --

    public MiniBadgeNodeTrainer(GradientDescentParams config) {
        this.config = config;
    }

    //endregion

    //region -- methods --

    public void updateBiasWeightCache(INeuralNet neuralNet) {

        INeuralNetCore core = neuralNet.getNeuralNetCore();
        nodePrevBiasesMapper = new HashMap<>(NeuralNetCoreUtils.getNumberOfNodes(core));
        axonPrevWeightsMapper = new HashMap<>(NeuralNetCoreUtils.getNumberOfParentAxons(core));

        List<INode> nodeList = NeuralNetCoreUtils.getAllNodesLinewiseBottomToTop(core);

        for(INode node : nodeList) {
            nodePrevBiasesMapper.put(node, 0.);
            for(IAxon axon : node.getParentAxons()){
                axonPrevWeightsMapper.put(axon, 0.);
            }
        }
    }

    public void propagateOutputNodeBackward(INode outputNode, INode expectedNode) {

        if(outputNode.getActivationFunctionContainer() == null) {
            throw new RuntimeException("MiniBadgeNodeTrainer.propagateOutputNodeBackward: outputNode needs ActivationFunctionContainer != null");
        }

        double error = outputNode.getActivation()-expectedNode.getActivation();
        outputNode.setError(error);

        processMiniBadge(outputNode);
    }

    public void propagateBackward(INode node) {

        if(node.getActivationFunctionContainer() == null) {
            return;
        }

        double error = 0.;
        for(IAxon axon : node.getChildAxons()) {
            double product = axon.getWeight() * axon.getChildNode().getError();
            error += product;
        }

        DoubleFunction<Double> derivative = node.getActivationFunctionContainer().getDerivative();
        node.setError(error * derivative.apply(node.getWeightedInput()));

        processMiniBadge(node);
    }

    private void processMiniBadge(INode node) {

        currentBadgeSize++;
        updateBias(node);
        updateWeights(node);
    }

    private void updateBias(INode node) {

        int badgeSize = config.getBadgeSize();

        nodePrevBiasesMapper.put(node, nodePrevBiasesMapper.get(node) + node.getError());

        if(currentBadgeSize % badgeSize == 0) {
            double newBias = node.getBias() - config.getLearningRate()*nodePrevBiasesMapper.get(node)/badgeSize;

            synchronized(synchronizer) {
                node.setBias(newBias);
                nodePrevBiasesMapper.put(node, 0.);
            }
        }
    }

    private void updateWeights(INode node) {

        int badgeSize = config.getBadgeSize();

        for(IAxon axon : node.getParentAxons()) {

            double weightUpdate = axonPrevWeightsMapper.get(axon) + node.getError()*axon.getParentNode().getActivation();
            axonPrevWeightsMapper.put(axon, weightUpdate);

            if(currentBadgeSize % badgeSize == 0) {

                double newWeight = axon.getWeight() - config.getLearningRate()/badgeSize*axonPrevWeightsMapper.get(axon);

                synchronized (synchronizer){
                    axon.setWeight(newWeight);
                    axonPrevWeightsMapper.put(axon, 0.);
                }
            }
        }
    }

    //endregion
}