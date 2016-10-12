package de.baleipzig.iris.persistence.repository;

import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface INeuralNetEntityRepository extends MongoRepository<NeuralNetEntity, String> {

    NeuralNetEntity findByName(String name);
    NeuralNetEntity findByNeuralNetId(String neuralNetId);

}
