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
import org.springframework.context.annotation.Scope;

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
    @Scope("prototype")
    public ILayer getLayer() { return new Layer(); }

    @Bean
    @Scope("prototype")
    public INeuralNet getNeuralNet() { return new NeuralNet(); }

    @Bean
    @Scope("prototype")
    public INeuralNetCore getNeuralNetCore() { return new NeuralNetCore(); }

    @Bean
    @Scope("prototype")
    public INeuralNetMetaData getNeuralNetMetaData() { return new NeuralNetMetaData(); }

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }
}
