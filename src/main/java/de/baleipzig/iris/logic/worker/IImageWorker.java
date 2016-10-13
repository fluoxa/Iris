package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.ImageType;
import de.baleipzig.iris.model.image.IImage;

import java.util.List;
import java.util.UUID;

public interface IImageWorker extends ICrudWorker<IImage, UUID> {

    void exportImageToDb();

    long countImagesByType(ImageType imageType);

    List<IImage> loadRandomImagesByType(int imageCount, ImageType imageType);

    List<IImage> loadAllImagesByType(ImageType imageType);
}
