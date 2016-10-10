package de.baleipzig.iris.logic;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetWorker implements INeuralNetWorker {

    private final INeuralNetEntityRepository repository;

    @Override
    public void save(INeuralNet neuralNet) {

    }

    @Override
    public INeuralNet load(String neuralNetId) {
        return null;
    }

    @Override
    public void delete(String neuralNetId) {
        repository.delete(neuralNetId);
    }
}
