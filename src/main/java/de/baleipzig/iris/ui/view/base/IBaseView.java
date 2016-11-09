package de.baleipzig.iris.ui.view.base;

import com.vaadin.navigator.View;
import de.baleipzig.iris.configuration.LanguageConfiguration;
import de.baleipzig.iris.ui.language.LanguageHandler;
import de.baleipzig.iris.ui.presenter.base.BasePresenter;

import java.util.List;

public interface IBaseView<P extends BasePresenter> extends View {

    LanguageHandler getLanguageHandler();
    void setAvailableLanguages(List<LanguageConfiguration.Language> languages);
    void setSelectedLanguage(LanguageConfiguration.Language language);
    void reload();
}
