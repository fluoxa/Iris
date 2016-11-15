package de.baleipzig.iris.configuration.spring;

import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.*;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImage;

@Configuration
public class BeanConfiguration {

    @Bean
    public IEntityLayerAssembler<BufferedImage> imageAssembler(){
        return new ImageAssembler();
    }

    @Bean
    public IAssembler<Integer> digitAssembler() {
        return new DigitAssembler();
    }

    @Bean
    public ILayer getLayer() { return new Layer(); }

    @Bean
    public INeuralNet getNeuralNet() { return new NeuralNet(); }

    @Bean
    public INeuralNetCore getNeuralNetCore() { return new NeuralNetCore(); }

    @Bean
    public INeuralNetMetaData getNeuralNetMetaData() { return new NeuralNetMetaData(); }

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }
}
