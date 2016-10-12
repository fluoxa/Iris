package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.logic.converter.NeuralNetConverter;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetMetaData;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNet;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetWorker implements INeuralNetWorker {

    private final INeuralNetEntityRepository repository;

    public void save(INeuralNet neuralNet) {

        NeuralNetEntity neuralNetEntity = NeuralNetConverter.toNeuralNetEntity(neuralNet);
        repository.save(neuralNetEntity);
    }

    public INeuralNet load(UUID neuralNetId) {

        NeuralNetEntity neuralNetEntity = repository.findByNeuralNetId(neuralNetId.toString());

        if(neuralNetEntity == null) {
            return null;
        }

        INeuralNetCore core = NeuralNetConverter.fromNeuralNetCoreEntity(neuralNetEntity);
        INeuralNetMetaData metaData = NeuralNetConverter.fromMetaDataEntity(neuralNetEntity);
        INeuralNet net = new NeuralNet();
        net.setNeuralNetCore(core);
        net.setNeuralNetMetaData(metaData);

        return net;
    }

    public void delete(UUID neuralNetId) {
        repository.delete(neuralNetId.toString());
    }
}