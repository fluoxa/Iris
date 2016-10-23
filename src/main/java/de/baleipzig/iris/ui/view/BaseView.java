package de.baleipzig.iris.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.ui.presenter.IPresenter;

public abstract class BaseView<P extends IPresenter> extends CssLayout implements View {
    protected P presenter;


    private final VerticalLayout headerBodyLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();

    public BaseView() {
        ThemeResource imageResource = new ThemeResource("img/logo.png");
        Image logoImage = new Image(null, imageResource);
        logoImage.setHeight("90px");
        headerLayout.setMargin(true);
        headerLayout.addComponent(logoImage);

        headerBodyLayout.addComponent(headerLayout);
        addComponent(headerBodyLayout);
    }
}
