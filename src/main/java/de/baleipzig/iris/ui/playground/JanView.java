package de.baleipzig.iris.ui.playground;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.utils.NeuralNetCoreUtils;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.activationfunction.ActivationFunction;
import de.baleipzig.iris.model.neuralnet.activationfunction.ActivationFunctionContainerFactory;
import de.baleipzig.iris.model.neuralnet.activationfunction.IActivationFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.Axon;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.neuralnet.*;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.model.neuralnet.node.Node;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;


@UIScope
@SpringView(name = JanView.VIEW_NAME)
public class JanView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "jan";

    @Autowired
    private INeuralNetWorker neuralNetWorker;

    @PostConstruct
    void init() {
        this.addComponent(new Label("Jans Spielwiese"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        INeuralNet net = createNeuralNet();

        UUID id = net.getNeuralNetMetaData().getId();

        neuralNetWorker.save(net);

        INeuralNet loadedNet = neuralNetWorker.load(id);

        System.out.printf("Anzahl der Knoten: " + NeuralNetCoreUtils.getNumberOfNodes(loadedNet.getNeuralNetCore()));

        neuralNetWorker.delete(id);
    }

    private INeuralNet createNeuralNet(){

        IActivationFunctionContainer func = ActivationFunctionContainerFactory.getContainer(ActivationFunction.sigmoid);

        INode node1 = new Node(func);
        node1.setBias(1.);
        INode node2 = new Node(func);
        node2.setBias(2.);
        ILayer inputLayer = new Layer();
        inputLayer.resize(new Dimension(2,1));
        inputLayer.addNode(node1);
        inputLayer.addNode(node2);

        INode node3 = new Node(func);
        IAxon axon13 = new Axon();
        axon13.setParentNode(node1);
        axon13.setChildNode(node3);
        axon13.setWeight(2.3);
        node3.addParentAxon(axon13);

        IAxon axon23 = new Axon();
        axon23.setChildNode(node3);
        axon23.setParentNode(node2);
        axon23.setWeight(-1.3);
        node3.addParentAxon(axon23);

        ILayer hiddenLayer = new Layer();
        hiddenLayer.resize(new Dimension(1,1));
        hiddenLayer.addNode(node3);

        INode node4 = new Node(func);
        IAxon axon34 = new Axon();
        axon34.setChildNode(node4);
        axon34.setParentNode(node3);
        axon34.setWeight(1.3);
        node3.addChildAxon(axon34);
        node4.addParentAxon(axon34);

        ILayer outputLayer = new Layer();
        outputLayer.resize(new Dimension(1,1));
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

}
