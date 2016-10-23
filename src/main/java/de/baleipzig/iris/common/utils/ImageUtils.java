package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.model.image.IImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageUtils {

    public static Map<BufferedImage, Integer> convertToResultMap(List<IImage> imageList) {

        Map<BufferedImage, Integer> mapper = new HashMap<>(imageList.size());

        imageList.forEach( iImage -> {

            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(iImage.getImageAsBase64());
            BufferedImage img = null;

            try{
                img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            }
            catch(IOException ex){
                System.err.println(ex);
            }

            mapper.put(img, iImage.getDigit());
        });

        return mapper;
    }
}