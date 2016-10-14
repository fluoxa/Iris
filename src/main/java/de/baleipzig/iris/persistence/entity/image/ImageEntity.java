package de.baleipzig.iris.persistence.entity.image;

import de.baleipzig.iris.ImageType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class ImageEntity {

    @Id
    private UUID imageEntityId = UUID.randomUUID();
    private String name = "";
    private String imageType = ImageType.TRAIN.toString();
    private int digit = 0;
    private int imageNumber = 0;
    private String imageAsBase64 = "";
}
