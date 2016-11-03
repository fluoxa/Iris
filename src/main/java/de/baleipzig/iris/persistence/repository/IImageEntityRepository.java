package de.baleipzig.iris.persistence.repository;

import de.baleipzig.iris.persistence.entity.image.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface IImageEntityRepository extends MongoRepository<ImageEntity, UUID> {

    long countByImageType(String imageType);

    List<ImageEntity> findAllByImageType(String imageType);
}
