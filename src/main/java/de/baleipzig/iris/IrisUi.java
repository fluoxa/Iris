package de.baleipzig.iris;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dgl on 06.10.2016.
 */
@Theme("iris")
@SpringUI(path = "")
public class IrisUi extends UI {

    private HorizontalLayout mainLayout = new HorizontalLayout();

    @Override
    protected void init(VaadinRequest request) {
        mainLayout.addComponent(new Button("test1"));
        mainLayout.addComponent(new Button("test2"));

        Button button = new Button("test3");
        button.addClickListener(event -> System.out.println("adfasdfdsaf"));

        mainLayout.addComponent(button);


        this.setContent(mainLayout);
    }

}
