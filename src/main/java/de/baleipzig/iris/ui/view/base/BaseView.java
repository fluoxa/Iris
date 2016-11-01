package de.baleipzig.iris.ui.view.base;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.ui.presenter.base.BasePresenter;

import java.util.List;

public abstract class BaseView<P extends BasePresenter> extends CssLayout implements IBaseView<P> {

    private final VerticalLayout headerBodyLayout = new VerticalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();
    protected P presenter;

    public BaseView() {
        List l;
        ThemeResource imageResource = new ThemeResource("img/logo.png");
        Image logoImage = new Image(null, imageResource);
        logoImage.setHeight("90px");
        headerLayout.setMargin(true);
        headerLayout.addComponent(logoImage);

        headerBodyLayout.addComponent(headerLayout);
        addComponent(headerBodyLayout);
    }
}
