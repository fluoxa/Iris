package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import de.baleipzig.iris.common.utils.NeuralNetCoreUtils;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleFunction;

public class MiniBadgeNodeTrainer implements IMiniBadgeNodeTrainer {

    private GradientDescentConfig config = null;
    private Map<INode, List<Double>> nodePrevBiasesMapper = null;
    private Map<IAxon, List<Double>> axonPrevWeightsMapper = null;

    //region -- constructor --

    public MiniBadgeNodeTrainer(GradientDescentConfig config) {
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
            nodePrevBiasesMapper.put(node, new ArrayList<>(config.getBadgeSize()));
            for(IAxon axon : node.getParentAxons()){
                axonPrevWeightsMapper.put(axon, new ArrayList<>(config.getBadgeSize()));
            }
        }
    }

    public void propagateOutputNodeBackward(INode outputNode, INode expectedNode) {

        if(outputNode.getActivationFunctionContainer() == null) {
            throw new RuntimeException("MiniBadgeNodeTrainer.propagateOutputNodeBackward: outputNode needs ActivationFunctionContainer != null");
        }

        DoubleFunction<Double> derivative = outputNode.getActivationFunctionContainer().getDerivative();

        double error = (outputNode.getActivation()-expectedNode.getActivation()) * derivative.apply(outputNode.getWeightedInput());
        outputNode.setError(error);

        updateBias(outputNode);
        updateWeights(outputNode);
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

        updateBias(node);
        updateWeights(node);
    }

    private void updateBias(INode node) {

        int badgeSize = config.getBadgeSize();

        List<Double> prevBiases = nodePrevBiasesMapper.get(node);

        if(prevBiases == null){
            prevBiases = new ArrayList<>(badgeSize);
        }

        prevBiases.add(node.getError());

        if(prevBiases.size() == badgeSize) {

            double error = 0.;

            for(double prevBias : prevBiases){
                error += prevBias;
            }

            node.setBias(node.getBias()-config.getLearningRate()/badgeSize * error);
            prevBiases.clear();
        }
    }

    private void updateWeights(INode node) {

        int badgeSize = config.getBadgeSize();

        for(IAxon axon : node.getParentAxons()) {

            List<Double> prevWeights = axonPrevWeightsMapper.get(axon);

            if(prevWeights == null){
                prevWeights = new ArrayList<>(badgeSize);
            }

            double nodeWeight = node.getError()*axon.getParentNode().getActivation();
            prevWeights.add(nodeWeight);

            double error = 0.;

            for(double prevWeight : prevWeights){
                error += prevWeight;
            }

            axon.setWeight(axon.getWeight()-config.getLearningRate()/badgeSize * error);
            prevWeights.clear();
        }
    }

    //endregion
}