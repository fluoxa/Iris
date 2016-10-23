package de.baleipzig.iris.model.image;

import de.baleipzig.iris.enums.ImageType;
import lombok.Data;

@Data
public class Image implements IImage {

    private String name = "";
    private ImageType imageType = ImageType.TRAIN;
    private int digit = 0;
    private int imageNumber = 0;
    private String imageAsBase64 = "";

}
