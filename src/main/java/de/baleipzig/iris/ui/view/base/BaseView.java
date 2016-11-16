package de.baleipzig.iris.ui.view.base;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.configuration.LanguageConfiguration;
import de.baleipzig.iris.ui.presenter.base.BasePresenter;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class BaseView<P extends BasePresenter> extends HorizontalLayout implements IBaseView<P> {

    protected static int MARGIN_WIDTH = 12;

    private final Panel headerPanel = new Panel();
    private final HorizontalLayout bodyLayout = new HorizontalLayout();
    private final ComboBox languageComboBox = new ComboBox();
    protected P presenter;

    @PostConstruct
    private void init() {

        createLayout();
    }

    private void createLayout() {
        ThemeResource imageResource = new ThemeResource("img/logo.png");
        Image logoImage = new Image(null, imageResource);
        logoImage.addStyleName("iris-logo-image");

        UI.getCurrent().setLocale(getLanguageHandler().getLocale());

        Label applicationLabel = new Label(getLanguageHandler().getTranslation("base.application.name"));
        applicationLabel.addStyleName("iris-name-label");

        languageComboBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        languageComboBox.setNullSelectionAllowed(false);
        languageComboBox.setTextInputAllowed(false);
        languageComboBox.setWidth("90px");
        languageComboBox.addValueChangeListener(e -> presenter.changeLanguage((LanguageConfiguration.Language) e.getProperty().getValue()));

        final HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.addStyleName("iris-header-layout");
        headerLayout.setWidth("100%");
        headerLayout.setMargin(true);

        headerLayout.addComponent(logoImage);
        headerLayout.addComponent(applicationLabel);
        headerLayout.addComponent(languageComboBox);
        headerLayout.setExpandRatio(logoImage, 0);

        headerLayout.setExpandRatio(applicationLabel, 1);
        headerLayout.setExpandRatio(languageComboBox, 0);
        headerLayout.setComponentAlignment(applicationLabel, Alignment.MIDDLE_CENTER);

        headerPanel.setContent(headerLayout);
        headerPanel.setHeight("106px");

        bodyLayout.setMargin(new MarginInfo(true, false, false, false));
        bodyLayout.setSizeFull();

        final VerticalLayout headerAndBodyLayout = new VerticalLayout();
        headerAndBodyLayout.setSizeFull();

        headerAndBodyLayout.addComponent(headerPanel);
        headerAndBodyLayout.addComponent(bodyLayout);
        headerAndBodyLayout.setExpandRatio(headerPanel, 0);
        headerAndBodyLayout.setExpandRatio(bodyLayout, 1);

        setSizeFull();
        setMargin(true);
        addComponent(headerAndBodyLayout);
    }

    public void setAvailableLanguages(List<LanguageConfiguration.Language> languages) {

        languageComboBox.clear();
        BeanItemContainer<LanguageConfiguration.Language> languagesAsContainer = new BeanItemContainer<>(LanguageConfiguration.Language.class, languages);
        languageComboBox.setContainerDataSource(languagesAsContainer);
        languageComboBox.setItemCaptionPropertyId("displayName");
    }

    public void setSelectedLanguage(LanguageConfiguration.Language language) {

        languageComboBox.setValue(language);
    }

    public void reload() {

        Page.getCurrent().reload();
    }

    protected void setBodyContent(Component content) {

        bodyLayout.removeAllComponents();
        bodyLayout.addComponent(content);
    }

    protected Dimension getEstimatedBodyLayoutDim() {

        int browserHeight = UI.getCurrent().getPage().getBrowserWindowHeight();
        int browserWidth = UI.getCurrent().getPage().getBrowserWindowWidth();

        int bodyLayoutHeight = browserHeight - Math.round(headerPanel.getHeight()) - 3*MARGIN_WIDTH;
        int bodyLayoutWidth = browserWidth - 2*MARGIN_WIDTH;


        Dimension dimension = new Dimension(bodyLayoutHeight, bodyLayoutWidth);
        return dimension;
    }
}
