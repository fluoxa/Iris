package de.baleipzig.iris.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.iris.example")
public class ExampleConfiguration {

    @Data
    public static class Pojo {
        private String name = "";
        private String info = "";
    }

    private String exampleString = "";

    private boolean exampleBoolean;

    private List<String> exampleListWithStrings = new ArrayList<>();

    private List<Pojo> exampleListWithPojo = new ArrayList<>();

    private Map<String, Pojo> exampleMapWithPojo = new HashMap<>();

    private Map<String, String> exampleMapWithString = new HashMap<>();
}
