package de.baleipzig.iris.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.iris.languageconfiguration")
public class LanguageConfiguration {

    @Data
    public static class Language {
        private String localeKey;
        private String displayName;
    }

    private List<Language> languages = new ArrayList<>();
}
