package de.baleipzig.iris.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.logic.INeuralNetWorker;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @Autowired
    INeuralNetEntityRepository repository;

    @Autowired
    INeuralNetWorker neuralNetWorker;

    @PostConstruct
    void init() {
        Button button = new Button("create Test entity");
        button.addClickListener(clickEvent -> createTestEntity());
        addComponent(new Label("This is the default view"));
        addComponent(button);
    }

    private void createTestEntity() {

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
