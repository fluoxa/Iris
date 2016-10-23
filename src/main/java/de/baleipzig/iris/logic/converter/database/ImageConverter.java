package de.baleipzig.iris.logic.converter.database;

import de.baleipzig.iris.model.image.IImage;
import de.baleipzig.iris.model.image.Image;
import de.baleipzig.iris.persistence.entity.image.ImageEntity;
import org.dozer.DozerBeanMapper;

public class ImageConverter {

    private static final DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public static IImage fromEntity(ImageEntity imageEntity) {
        Image image = new Image();
        dozerBeanMapper.map(imageEntity, image);
        return image;
    }
}
