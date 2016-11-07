package de.baleipzig.iris.ui.language;

import com.vaadin.spring.annotation.UIScope;
import de.baleipzig.iris.configuration.LanguageConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@UIScope
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LanguageHandler {
    private final ApplicationContext context;
    private final LanguageConfiguration languageConfiguration;

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

    public void setLanguage(LanguageConfiguration.Language language) {
        if (language == null) {
            throw new RuntimeException("languageEntry cant be null");
        }

        Locale localeByLanguage =  new Locale(language.getLocaleKey());

        if(localeByLanguage == null) {
            throw new RuntimeException(language + " is not a valid entry");
        }
        this.language = language;
        locale = localeByLanguage;

    }

    public String getTranslation(String translationKey) {
        return getTranslation(translationKey, null);
    }


    public String getTranslation(String translationKey, Object[] args) {
        return context.getMessage(translationKey, args, locale);
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
}
