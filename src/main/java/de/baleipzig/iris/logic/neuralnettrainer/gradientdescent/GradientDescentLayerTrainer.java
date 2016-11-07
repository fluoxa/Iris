package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GradientDescentLayerTrainer implements IGradientDescentLayerTrainer {

    //region -- member --

    private final IGradientDescentNodeTrainer nodeTrainer;

    //endregion

    //region -- methods --

    public void propagateOutputLayerBackward(ILayer outputLayer, ILayer expectedResultLayer) {

        if(!outputLayer.getDim().equals(expectedResultLayer.getDim())) {
            throw new RuntimeException("Layer.propagateOutputLayerBackwards: Layer must have matching dimensions");
        }

        int sizeX = outputLayer.getDim().getX();
        int sizeY = outputLayer.getDim().getY();

        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {
                nodeTrainer.propagateOutputNodeBackward(outputLayer.getNode(x,y), expectedResultLayer.getNode(x,y));
            }
        }
    }

    public void propagateBackward(ILayer layer) {

        layer.applyToLayerNodes(nodeTrainer::propagateBackward);
    }

    //endregion
}