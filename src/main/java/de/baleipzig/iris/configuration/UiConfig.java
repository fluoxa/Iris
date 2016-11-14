package de.baleipzig.iris.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.iris.uiconfiguration")
public class UiConfig {

    private int progressUpdateInterval;
}
