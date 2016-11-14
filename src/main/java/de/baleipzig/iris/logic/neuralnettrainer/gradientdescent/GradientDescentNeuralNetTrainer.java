package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import lombok.AllArgsConstructor;

import java.util.ListIterator;

@AllArgsConstructor
public class GradientDescentNeuralNetTrainer implements IGradientDescentNeuralNetTrainer {

    //region -- member --

    private final IGradientDescentLayerTrainer layerTrainer;

    //endregion

    //region -- methods --

    public void propagateBackward(INeuralNet neuralNet, ILayer expectedResultLayer) {

        INeuralNetCore netCore = neuralNet.getNeuralNetCore();

        layerTrainer.propagateOutputLayerBackward(netCore.getOutputLayer(), expectedResultLayer);

        int numberOfHiddenLayer = netCore.getHiddenLayers().size();
        ListIterator<ILayer> lastHiddenLayer = netCore.getHiddenLayers().listIterator(numberOfHiddenLayer);
        while(lastHiddenLayer.hasPrevious()) {

            layerTrainer.propagateBackward(lastHiddenLayer.previous());
        }

        layerTrainer.propagateBackward(netCore.getInputLayer());
    }

    //endregion
}