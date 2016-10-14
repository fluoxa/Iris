package de.baleipzig.iris.model.image;

import de.baleipzig.iris.ImageType;

public interface IImage {
    String getName();
    void setName(String name);

    ImageType getImageType();
    void setImageType(ImageType imageType);

    int getDigit();
    void setDigit(int digit);

    int getImageNumber();
    void setImageNumber(int imageNumber);

    String getImageAsBase64();
    void setImageAsBase64(String imageAsBase64);
}
