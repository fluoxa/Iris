package de.baleipzig.iris.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.iris.neuralnetconfig")
public class NeuralNetConfig {

    private String pathTrainingImages="";
    private String pathTestImages="";
    private double learningRate;
    private int trainingCycles;
    private int badgeSize;
}