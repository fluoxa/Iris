package de.baleipzig.iris.ui.presenter.base;

import de.baleipzig.iris.configuration.LanguageConfiguration;
import de.baleipzig.iris.ui.service.base.IBaseService;
import de.baleipzig.iris.ui.view.base.IBaseView;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class BasePresenter<V extends IBaseView, S extends IBaseService> {

    protected final V view;
    protected final S service;

    public void init() {
        view.setAvailableLanguages(service.getLanguageHandler().getAvailableLanguages());
        view.setSelectedLanguage(service.getLanguageHandler().getLanguage());
    }

    public void runEventAsynchronously(Supplier<Void> call) {

        (new Thread(call::get)).start();
    }

    public void changeLanguage(LanguageConfiguration.Language language) {
        if (!service.getLanguageHandler().getLanguage().equals(language)) {
            service.getLanguageHandler().setLanguage(language);
            view.reload();
        }
    }
}