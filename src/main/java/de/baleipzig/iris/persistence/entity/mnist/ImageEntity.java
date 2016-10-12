package de.baleipzig.iris.persistence.entity.mnist;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ImageEntity {

    @Id
    private String name = "";
    private ImageType imageType = ImageType.train;
    private int digit = 0;
    private int imageNumber = 0;
    private String imageAsBase64 = "";

    public enum ImageType {
        train, test
    }
}
