package de.baleipzig.iris.configuration.spring;

import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
import org.dozer.DozerBeanMapper;
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
    public IAssembler<Integer> digitAssembler() {
        return new DigitAssembler();
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }
}
