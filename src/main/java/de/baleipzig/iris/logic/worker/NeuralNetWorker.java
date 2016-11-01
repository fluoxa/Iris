package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.logic.converter.database.NeuralNetConverter;
import de.baleipzig.iris.model.neuralnet.neuralnet.*;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import de.baleipzig.iris.persistence.subset.NeuralNetSubset;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetWorker implements INeuralNetWorker {

    private final INeuralNetEntityRepository neuralNetEntityRepository;
    private final ILayerWorker layerWorker;

    private final DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public void save(INeuralNet neuralNet) {

        NeuralNetEntity neuralNetEntity = NeuralNetConverter.toNeuralNetEntity(neuralNet);
        neuralNetEntityRepository.save(neuralNetEntity);
    }

    public INeuralNet load(UUID neuralNetId) {

        NeuralNetEntity neuralNetEntity = neuralNetEntityRepository.findByNeuralNetId(neuralNetId.toString());

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

        neuralNetEntityRepository.delete(neuralNetId.toString());
    }

    @Override
    public List<NeuralNetMetaData> findByName(String name) {
        List<NeuralNetSubset> neuralNetSubsets = neuralNetEntityRepository.findAllByNameLike(name);
        List<NeuralNetMetaData> neuralNetMetaDatas = new ArrayList<>(neuralNetSubsets.size());

        neuralNetSubsets.forEach(neuralNetSubset -> {
            NeuralNetMetaData neuralNetMetaData = new NeuralNetMetaData();
            dozerBeanMapper.map(neuralNetSubset, neuralNetMetaData);
            neuralNetMetaDatas.add(neuralNetMetaData);
        });
        return neuralNetMetaDatas;
    }

    public void propagateForward(INeuralNet neuralNet) {

        INeuralNetCore neuralNetCore = neuralNet.getNeuralNetCore();

        neuralNetCore.getHiddenLayers().forEach(layerWorker::propagateForward);
        layerWorker.propagateForward(neuralNetCore.getOutputLayer());
    }
}