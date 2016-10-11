package de.baleipzig.iris.logic;

import de.baleipzig.iris.logic.converter.NeuralNetConverter;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetMetaData;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNet;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetWorker implements INeuralNetWorker {

    private final INeuralNetEntityRepository repository;

    public void save(INeuralNet neuralNet) {

        NeuralNetEntity neuralNetEntity = NeuralNetConverter.toNeuralNetEntity(neuralNet);
        repository.save(neuralNetEntity);
    }

    public INeuralNet load(String neuralNetId) {

        NeuralNetEntity neuralNetEntity = repository.findByName(neuralNetId);
        INeuralNetCore core = NeuralNetConverter.fromNeuralNetCoreEntity(neuralNetEntity);
        INeuralNetMetaData metaData = NeuralNetConverter.fromMetaDataEntity(neuralNetEntity);
        INeuralNet net = new NeuralNet();
        net.setNeuralNetCore(core);
        net.setNeuralNetMetaData(metaData);

        return net;
    }

    public void delete(String neuralNetId) {
        repository.delete(neuralNetId);
    }
}