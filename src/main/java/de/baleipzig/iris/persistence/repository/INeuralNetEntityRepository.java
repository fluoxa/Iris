package de.baleipzig.iris.persistence.repository;

import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.subset.NeuralNetSubset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface INeuralNetEntityRepository extends MongoRepository<NeuralNetEntity, String> {

    Page<NeuralNetSubset> findAllByNameNotNull(Pageable pageable);
    List<NeuralNetSubset> findAllByNameLikeIgnoreCase(String name);
    NeuralNetEntity findByName(String name);
    NeuralNetEntity findByNeuralNetId(String neuralNetId);
}
