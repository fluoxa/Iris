package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.model.neuralnet.activationfunction.IFunctionContainer;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageAssembler implements IEntityLayerAssembler<BufferedImage> {

    public ILayer convert(BufferedImage img, IFunctionContainer funcContainer) {

        Dimension imgDim = new Dimension(img.getWidth(null), img.getHeight(null));

        ILayer layer = LayerUtils.createLayerWithOptionalRandomBias(imgDim, funcContainer, false);

        copy(img, layer);

        return layer;
    }

    public void copy (BufferedImage img, ILayer layer) {

        Dimension imgDim = new Dimension(img.getWidth(null), img.getHeight(null));

        if(!layer.getDim().equals(imgDim)) {
            throw new RuntimeException("ImageConverter.copy: Image and Layer dimension have to match");
        }

        for(int y = 0; y < imgDim.getY(); y++){
            for(int x = 0; x < imgDim.getX(); x++) {

                Color color = new Color(img.getRGB(x,y));
                double activation = color.getRed()/255.;
                layer.getNode(x,y).setActivation(activation);
            }
        }
    }
}