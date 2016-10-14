package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.ImageType;
import de.baleipzig.iris.model.image.IImage;

import java.util.List;

public interface IImageWorker {

    void exportImageToDb();

    long countImagesByType(ImageType imageType);

    List<IImage> loadRandomImagesByType(int imageCount, ImageType imageType);

    List<IImage> loadAllImagesByType(ImageType imageType);
}
