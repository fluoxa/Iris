package de.baleipzig.iris.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.persistence.entity.neuralnet.AxonEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NodeEntity;
import de.baleipzig.iris.persistence.repository.NeuralNetEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hezamu.canvas.Canvas;

import javax.annotation.PostConstruct;
import java.io.IOException;

@UIScope
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @Autowired
    NeuralNetEntityRepository repository;

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

        NeuralNetEntity neuralNetEntity = new NeuralNetEntity();

        for(long i = 0; i < 5; i++) {
            NodeEntity nodeEntity = new NodeEntity();
            nodeEntity.setNodeId(i);
            neuralNetEntity.getNodes().put(nodeEntity.getNodeId(), nodeEntity);
        }

        neuralNetEntity.getNodes().forEach((parentNodeId, parentNode) -> {

            neuralNetEntity.getNodes().forEach((childNodeId, childNode)-> {
                if(parentNodeId != childNodeId) {
                    AxonEntity axonEntity = new AxonEntity();
                    axonEntity.setWeight(Math.random());
                    axonEntity.setParentNode(parentNodeId);
                    axonEntity.setChildNode(childNodeId);
                    neuralNetEntity.getAxons().put(parentNodeId + "-" + childNodeId ,axonEntity);
                }
            });
        });

        repository.save(neuralNetEntity);

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(neuralNetEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        XmlMapper xmlMapper = new XmlMapper();

        String xml = "";
        try {
            xml = xmlMapper.writeValueAsString(neuralNetEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NeuralNetEntity neuralNetEntityLoaded = repository.findOne(neuralNetEntity.getNeuralNetId());

        neuralNetEntityLoaded = null;
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
