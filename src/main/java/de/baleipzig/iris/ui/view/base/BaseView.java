package de.baleipzig.iris.ui.view.base;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.baleipzig.iris.ui.presenter.base.BasePresenter;

import javax.annotation.PostConstruct;

public abstract class BaseView<P extends BasePresenter> extends CssLayout implements IBaseView<P> {

    private final Panel headerPanel = new Panel();
    private final VerticalLayout headerAndBodyLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final CssLayout bodyLayout = new CssLayout();
    protected P presenter;

    @PostConstruct
    private void init() {
        System.out.println("BaseView");
        createLayout();
    }

    private void createLayout() {
        ThemeResource imageResource = new ThemeResource("img/logo.png");
        Image logoImage = new Image(null, imageResource);
        logoImage.addStyleName("application-logo");
        //logoImage.setHeight("80px");

        Label applicationLabel = new Label("Iris - Ziffernerkennung");
        applicationLabel.addStyleName("application-label");

        ComboBox languageComboBox = new ComboBox();

        headerLayout.setMargin(true);
        headerLayout.addComponent(logoImage);
        headerLayout.addComponent(applicationLabel);
        headerLayout.addComponent(languageComboBox);
        headerLayout.setExpandRatio(logoImage, 0);
        headerLayout.setExpandRatio(applicationLabel, 1);
        headerLayout.setExpandRatio(languageComboBox, 0);
        headerLayout.setComponentAlignment(applicationLabel, Alignment.MIDDLE_CENTER);
        headerLayout.setWidth("100%");

        headerPanel.setContent(headerLayout);

        bodyLayout.setStyleName(ValoTheme.PANEL_BORDERLESS);

        headerAndBodyLayout.addComponent(headerPanel);
        headerAndBodyLayout.addComponent(bodyLayout);

        addStyleName(ValoTheme.PANEL_BORDERLESS);
        //setMargin(true);
        addStyleName("iris-main-panel");
        addComponent(headerAndBodyLayout);
    }

    public void setBodyContent(Component content) {
        bodyLayout.removeAllComponents();
        bodyLayout.addComponent(content);
    }
}
