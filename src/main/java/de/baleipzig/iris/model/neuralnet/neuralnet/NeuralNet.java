package de.baleipzig.iris.model.neuralnet.neuralnet;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NeuralNet implements INeuralNet {

    //region -- member --

    private ILayer inputLayer = null;
    private ILayer outputLayer = null;
    private List<ILayer> hiddenLayer = null;

    //endregion

    //region -- constructor --

    public NeuralNet(){
        hiddenLayer = new ArrayList<>();
    }

    //endregion

    //region -- methods --

    public void addHiddenLayer(ILayer layer){
        hiddenLayer.add(layer);
    }

    //endregion
}