package de.baleipzig.iris.configuration.spring;

import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImage;

@Configuration
public class AssemblerConfiguration {

    @Bean
    public IEntityLayerAssembler<BufferedImage> imageAssembler(){
        return new ImageAssembler();
    }

    @Bean
    public IEntityLayerAssembler<Integer> digitAssembler() {
        return new DigitAssembler();
    }

}
