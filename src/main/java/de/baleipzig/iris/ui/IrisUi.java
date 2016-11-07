package de.baleipzig.iris.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import de.baleipzig.iris.configuration.LanguageConfiguration;
import de.baleipzig.iris.ui.language.LanguageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("iris")
@SpringUI
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Widgetset("de.baleipzig.iris.IrisWidgetset")
public class IrisUi extends UI {

    public static String SESSION_KEY_LANGUAGE = "languageKey";

    private final SpringViewProvider viewProvider;
    private final LanguageHandler languageHandler;

    @Override
    protected void init(VaadinRequest request) {

        //initLanguageHandler();

        final CssLayout root = new CssLayout();
        root.setSizeFull();
        setContent(root);

        Navigator navigator = new Navigator(this, root);
        navigator.addProvider(viewProvider);
    }

    private void initLanguageHandler() {
        Object languageEntryAsObject = this.getSession().getAttribute(SESSION_KEY_LANGUAGE);
        if(languageEntryAsObject != null && languageEntryAsObject instanceof LanguageConfiguration.Language) {
            languageHandler.setLanguage((LanguageConfiguration.Language) languageEntryAsObject);
        }

        languageHandler.setLanguage(languageHandler.getAvailableLanguages().get(1));
    }


}
