package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.enums.NeuralNetCoreType;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;

import java.util.List;
import java.util.UUID;

public interface INeuralNetWorker extends ICrudWorker<INeuralNet, UUID> {

    INeuralNet createImageDigitNet(List<Dimension> hiddenDimensions);

    String toJson(INeuralNet net);
    INeuralNet fromJson(String jsonString, NeuralNetCoreType neuralNetCoreType);

    List<NeuralNetMetaData> findAllNeuralNetMetaDataByName(String name);

    List<NeuralNetMetaData> findAllNeuralNetMetaDatas();

    void propagateForward(INeuralNet neuralNet);
}
