package de.baleipzig.iris.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.logic.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.ActivationFunctions;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.*;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;
import de.baleipzig.iris.persistence.repository.INeuralNetEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.DoubleFunction;

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

        INeuralNet net = createNeuralNet();

    }

    private INeuralNet createNeuralNet(){
        DoubleFunction<Double> func = ActivationFunctions::sigmoid;

        INode node1 = new Node(func);
        INode node2 = new Node(func);
        ILayer inputLayer = new Layer();
        inputLayer.addNode(node1);
        inputLayer.addNode(node2);

        INode node3 = new Node(func);
        IAxon axon13 = new Axon();
        axon13.setChildNode(node1);
        axon13.setParentNode(node3);
        axon13.setWeight(2.3);
        node3.addParentAxon(axon13);

        IAxon axon23 = new Axon();
        axon23.setChildNode(node2);
        axon23.setParentNode(node3);
        axon23.setWeight(-1.3);
        node3.addParentAxon(axon23);

        ILayer hiddenLayer = new Layer();
        hiddenLayer.addNode(node3);

        INode node4 = new Node(func);
        IAxon axon34 = new Axon();
        axon34.setChildNode(node3);
        axon34.setParentNode(node4);
        axon34.setWeight(1.3);
        node3.addChildAxon(axon34);
        node4.addParentAxon(axon34);

        ILayer outputLayer = new Layer();
        outputLayer.addNode(node4);

        INeuralNetCore net = new NeuralNetCore();
        net.setInputLayer(inputLayer);
        net.addHiddenLayer(hiddenLayer);
        net.setOutputLayer(outputLayer);

        INeuralNet totalNet = new NeuralNet();
        totalNet.setNeuralNetCore(net);
        totalNet.setNeuralNetMetaData(new NeuralNetMetaData());

        return totalNet;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
