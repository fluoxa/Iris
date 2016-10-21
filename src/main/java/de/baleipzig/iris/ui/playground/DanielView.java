package de.baleipzig.iris.ui.playground;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.configuration.ExampleConfiguration;
import de.baleipzig.iris.logic.worker.IImageWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hezamu.canvas.Canvas;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = DanielView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DanielView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "daniel";

    private final IImageWorker imageWorker;

    private final ExampleConfiguration exampleConfiguration;

    private boolean mouseDown = false;
    private int previousX;
    private int previousY;

    @PostConstruct
    void init() {
        this.addComponent(new Label("Daniels Spielwiese"));

        Button button = new Button("test");

        button.addClickListener(clickEvent -> {
            imageWorker.exportImageToDb();
//            long count = imageWorker.countImagesByType(ImageType.TEST);
//            count = 0;
            //List<IImage> list =  imageWorker.loadAllImagesByType(ImageType.TRAIN);
            // List<IImage> list = imageWorker.loadRandomImagesByType(1000, ImageType.TRAIN);
            String foo = "";
        });

        addComponent(button);

    }

    private void testCanvasAddon() {
        Label label = new Label("klicken und Maus bewegen im Rahmen :)");
        addComponent(label);

        Canvas canvas = new Canvas();
        canvas.setWidth("300px");
        canvas.setHeight("300px");
        canvas.setStrokeStyle(0, 0, 0);
        canvas.setLineWidth(10);
        canvas.setLineCap("round");
        canvas.addStyleName("iris-canvas");
        canvas.addMouseMoveListener(mouseEventDetails -> {
            int x = mouseEventDetails.getRelativeX();
            int y = mouseEventDetails.getRelativeY();

            System.out.println(x + "/" + y);

            if (mouseDown) {
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
