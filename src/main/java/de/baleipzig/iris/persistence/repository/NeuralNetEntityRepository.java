package de.baleipzig.iris.persistence.repository;

import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NeuralNetEntityRepository extends MongoRepository<NeuralNetEntity, String>{

    public NeuralNetEntity findByName(String name);

}
