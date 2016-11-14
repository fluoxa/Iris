package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.enums.NeuralNetCoreType;
import de.baleipzig.iris.logic.converter.database.NeuralNetConverter;
import de.baleipzig.iris.model.neuralnet.neuralnet.*;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetCoreEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import de.baleipzig.iris.persistence.subset.NeuralNetSubset;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetWorker implements INeuralNetWorker {

    //region -- member --

    private final INeuralNetEntityRepository neuralNetEntityRepository;
    private final ILayerWorker layerWorker;

    private final ApplicationContext context;

    private final ObjectMapper mapper = new ObjectMapper();

    //endregion

    //region -- methods --

    @Override
    public void save(INeuralNet neuralNet) {

        NeuralNetEntity neuralNetEntity = NeuralNetConverter.toNeuralNetEntity(neuralNet);
        neuralNetEntityRepository.save(neuralNetEntity);
    }

    @Override
    public INeuralNet load(UUID neuralNetId) {

        NeuralNetEntity neuralNetEntity = neuralNetEntityRepository.findByNeuralNetId(neuralNetId.toString());

        if(neuralNetEntity == null) {
            return null;
        }

        INeuralNetCore core = NeuralNetConverter.fromNeuralNetCoreEntity(neuralNetEntity.getNeuralNetCoreEntity(), NeuralNetCoreType.valueOf(neuralNetEntity.getType()));
        INeuralNetMetaData metaData = NeuralNetConverter.fromMetaDataEntity(neuralNetEntity);
        INeuralNet net = new NeuralNet();
        net.setNeuralNetCore(core);
        net.setNeuralNetMetaData(metaData);

        return net;
    }

    @Override
    public void delete(UUID neuralNetId) {

        neuralNetEntityRepository.delete(neuralNetId.toString());
    }

    @Override
    public String toJson(INeuralNet neuralNet) {

        String neuralNetJson = "";

        try {
            neuralNetJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(NeuralNetConverter.toNeuralNetCoreEntity(neuralNet.getNeuralNetCore()));
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        return neuralNetJson;
    }

    @Override
    public INeuralNet fromJson(String jsonString, NeuralNetCoreType neuralNetType) {

        INeuralNet neuralNet = context.getBean(INeuralNet.class);
        INeuralNetMetaData metaData = context.getBean(INeuralNetMetaData.class);

        neuralNet.setNeuralNetMetaData(metaData);

        NeuralNetCoreEntity neuralNetCoreEntity;

        try {
            neuralNetCoreEntity = mapper.readValue(jsonString, NeuralNetCoreEntity.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        neuralNet.setNeuralNetCore(NeuralNetConverter.fromNeuralNetCoreEntity(neuralNetCoreEntity, neuralNetType));

        return neuralNet;
    }


    @Override
    public List<NeuralNetMetaData> findAllNeuralNetMetaDataByName(String name) {
        List<NeuralNetSubset> neuralNetSubsets = neuralNetEntityRepository.findAllByNameLikeIgnoreCase(name);

        return NeuralNetConverter.fromNeuralNetSubsets(neuralNetSubsets);
    }

    @Override
    public List<NeuralNetMetaData> findAllNeuralNetMetaDatas() {
        Page<NeuralNetSubset> neuralNetSubsets = neuralNetEntityRepository.findAllByNameNotNull(new PageRequest(0, 50, Sort.Direction.ASC, "name"));
        return NeuralNetConverter.fromNeuralNetSubsets(neuralNetSubsets.getContent());
    }

    public void propagateForward(INeuralNet neuralNet) {

        INeuralNetCore neuralNetCore = neuralNet.getNeuralNetCore();

        neuralNetCore.getHiddenLayers().forEach(layerWorker::propagateForward);
        layerWorker.propagateForward(neuralNetCore.getOutputLayer());
    }

    //endregion
}