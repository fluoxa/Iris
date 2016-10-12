package de.baleipzig.iris.model.neuralnet.neuralnet;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NeuralNetCore implements INeuralNetCore {

    //region -- member --

    private ILayer inputLayer = null;
    private ILayer outputLayer = null;
    private List<ILayer> hiddenLayer = null;

    //endregion

    //region -- constructor --

    public NeuralNetCore(){
        hiddenLayer = new ArrayList<>();
    }

    //endregion

    //region -- methods --

    public void addHiddenLayer(ILayer layer){
        hiddenLayer.add(layer);
    }

    public List<ILayer> getHiddenLayers() {return hiddenLayer;}
    //endregion
}