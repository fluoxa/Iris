package de.baleipzig.iris.ui.language;

import com.vaadin.spring.annotation.VaadinSessionScope;
import de.baleipzig.iris.configuration.LanguageConfiguration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@VaadinSessionScope
public class LanguageHandler {

    private final ApplicationContext context;
    private final LanguageConfiguration languageConfiguration;

    @Getter
    private Locale locale;
    private LanguageConfiguration.Language language;

    @Autowired
    public LanguageHandler(ApplicationContext context, LanguageConfiguration languageConfiguration) {
        this.context = context;
        this.languageConfiguration = languageConfiguration;
        if(languageConfiguration.getLanguages() == null || languageConfiguration.getLanguages().isEmpty()) {
            throw new RuntimeException("languages not configured");
        }

        setLanguage(languageConfiguration.getLanguages().get(0));
    }

    public String getTranslation(String translationKey) {
        return getTranslation(translationKey, null);
    }

    public String getTranslation(String translationKey, Object[] args) {
        String translation = translationKey;
        try {
            translation = context.getMessage(translationKey, args, locale);
        } catch (NoSuchMessageException e) {
            System.out.println(e);
        }
        return translation;
    }

    public List<LanguageConfiguration.Language> getAvailableLanguages() {
        return languageConfiguration.getLanguages();
    }

    public LanguageConfiguration.Language getDefaultLanguage() {
        return languageConfiguration.getLanguages().get(0);
    }

    public LanguageConfiguration.Language getLanguage() {
        if(language != null) {
            return language;
        } else {
            return getDefaultLanguage();
        }
    }

    public void setLanguage(LanguageConfiguration.Language language) {
        if (language == null) {
            throw new RuntimeException("languageEntry cant be null");
        }

        Locale localeByLanguage = new Locale(language.getLocaleKey());

        this.language = language;
        locale = localeByLanguage;
    }
}