package de.baleipzig.iris.ui.playground;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = RonnyView.VIEW_NAME)
public class RonnyView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "ronny";

    @PostConstruct
    void init() {
        this.addComponent(new Label("Ronnys Spielwiese"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
