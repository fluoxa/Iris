package de.baleipzig.iris.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hezamu.canvas.Canvas;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @Autowired
    INeuralNetEntityRepository repository;

    @Autowired
    INeuralNetWorker neuralNetWorker;

    private boolean mouseDown = false;
    private int previousX;
    private int previousY;

    @PostConstruct
    void init() {
        Button button = new Button("create Test entity");
        button.addClickListener(clickEvent -> createTestEntity());
        addComponent(new Label("This is the default view"));
        addComponent(button);

        Label label = new Label("klicken und Maus bewegen im Rahmen :)");
        addComponent(label);

        Canvas canvas = new Canvas();
        canvas.setWidth("300px");
        canvas.setHeight("300px");
        canvas.setStrokeStyle(0,0,0);
        canvas.setLineWidth(10);
        canvas.setLineCap("round");
        canvas.addStyleName("iris-canvas");
        canvas.addMouseMoveListener(mouseEventDetails -> {
            int x = mouseEventDetails.getRelativeX();
            int y = mouseEventDetails.getRelativeY();

            System.out.println(x + "/" + y);

            if(mouseDown) {
                canvas.beginPath();
                canvas.moveTo(previousX, previousY);
                canvas.lineTo(x, y);
                canvas.stroke();
                canvas.closePath();
            }
            previousX = x;
            previousY = y;
        });

        canvas.addMouseDownListener(() -> {
            System.out.println("clicked");
            mouseDown = true;

        });


        canvas.addMouseUpListener(() -> {
            System.out.println("released");
            mouseDown = false;
        });

        addComponent(canvas);



    }

    private void createTestEntity() {

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
