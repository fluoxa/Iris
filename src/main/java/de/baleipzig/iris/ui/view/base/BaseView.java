package de.baleipzig.iris.ui.view.base;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import de.baleipzig.iris.ui.presenter.base.BasePresenter;

import javax.annotation.PostConstruct;

public abstract class BaseView<P extends BasePresenter> extends HorizontalLayout implements IBaseView<P> {

    private final HorizontalLayout bodyLayout = new HorizontalLayout();

    protected P presenter;

    @PostConstruct
    private void init() {
        createLayout();
    }

    private void createLayout() {
        ThemeResource imageResource = new ThemeResource("img/logo.png");
        Image logoImage = new Image(null, imageResource);
        logoImage.addStyleName("iris-logo-image");

        Label applicationLabel = new Label("Iris - Ziffernerkennung");
        applicationLabel.addStyleName("iris-name-label");

        ComboBox languageComboBox = new ComboBox();

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

        final Panel headerPanel = new Panel();
        headerPanel.setContent(headerLayout);

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

    protected void setBodyContent(Component content) {
        bodyLayout.removeAllComponents();
        bodyLayout.addComponent(content);
    }
}
