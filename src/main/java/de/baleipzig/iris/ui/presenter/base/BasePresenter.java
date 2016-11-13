package de.baleipzig.iris.ui.presenter.base;

import com.vaadin.ui.UI;
import de.baleipzig.iris.configuration.LanguageConfiguration;
import de.baleipzig.iris.ui.helper.UrlHelper;
import de.baleipzig.iris.ui.service.base.IBaseService;
import de.baleipzig.iris.ui.view.base.IBaseView;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
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

    public  Void navigateToView(String view, Map<String, String> parameters) {

        UI.getCurrent().getNavigator().navigateTo(UrlHelper.getUrl(view, parameters));
        return null;
    }

    public  Void navigateToView(String view) {

        UI.getCurrent().getNavigator().navigateTo(UrlHelper.getUrl(view, new HashMap<>()));
        return null;
    }
}