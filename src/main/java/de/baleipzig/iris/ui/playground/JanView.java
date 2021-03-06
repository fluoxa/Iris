package de.baleipzig.iris.ui.playground;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.logic.worker.IImageWorker;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.activationfunction.SigmoidFunctionContainer;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.Map;


@UIScope
@SpringView(name = JanView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JanView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "jan";

    private final INeuralNetWorker neuralNetWorker;
    private final IImageWorker imageWorker;

    Map<BufferedImage, Integer> trainMapper;
    Map<BufferedImage, Integer> testMapper;

    double numberOfCorrectPredictions = 0.;

    private boolean train = false;
    private Button createNNButton = new Button("train hard");
    private Button loadDBButton = new Button("loadDB");
    private Label resultLabel = new Label();
    private Label oldResultLabel = new Label();
    private Label iterationLabel = new Label();

    @PostConstruct
    void init() {
//        this.addComponent(new Label("Jans Spielwiese"));
        this.addComponent(createNNButton);
        this.addComponent(loadDBButton);
        createNNButton.addClickListener(e -> createNeuralNet());
        loadDBButton.addClickListener(e -> loadDB());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    private void loadDB() {

        imageWorker.exportImageToDb();
    }

    private void trainAndSaveNeuralNet() {

/*        INeuralNet neuralNet = createNeuralNet();

        Map<BufferedImage, Integer> resultMapper = ImageUtils.convertToResultMap(imageWorker.loadRandomImagesByType( 2500, ImageType.TRAIN));

        IEntityLayerAssembler<BufferedImage> imageConverter = new ImageAssembler();
        IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();
        GradientDescentParams config = new GradientDescentParams(2.,resultMapper.size(),1,5);
        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(config);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        INeuralNetTrainer<BufferedImage, Integer> trainer = new MiniBadgeTrainer<>(imageConverter, digitConverter,config, netTrainer, neuralNetWorker, nodeTrainer);

        trainer.setNeuralNet(neuralNet);
        trainer.train(resultMapper);

        INeuralNet trainedNet = trainer.getNeuralNet();
        trainedNet.getNeuralNetMetaData().setId(UUID.randomUUID());
        trainedNet.getNeuralNetMetaData().setName("trainingNet1");
        trainedNet.getNeuralNetMetaData().setDescription("trainedNet");*/


    }

    private void trainAndSaveNeuralNet(String id) {

//        INeuralNet neuralNet = neuralNetWorker.load(UUID.fromString(id));
//
//        trainMapper = ImageUtils.convertToResultMap(imageWorker.loadRandomImagesByType(5000,ImageType.TRAIN));
//
//        IEntityLayerAssembler<BufferedImage> imageConverter = new ImageAssembler();
//        IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();
//        GradientDescentParams config = new GradientDescentParams(3.,trainMapper.size(),5,30);
//        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(config);
//        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
//        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);
//
//        INeuralNetTrainer<BufferedImage, Integer> trainer = new MiniBadgeTrainer<>(imageConverter, digitConverter,config, netTrainer, neuralNetWorker, nodeTrainer);
//
//        trainer.setNeuralNet(neuralNet);
//        long millis = System.currentTimeMillis();
//        trainer.train(trainMapper);
//        System.out.println("Dauer: " + (System.currentTimeMillis() -millis));
//        neuralNetWorker.save(trainer.getNeuralNet());
    }

    private INeuralNet createNeuralNet(){

        ILayer inputLayer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(28,28), null, false);
        ILayer hiddenLayer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(10,10), new SigmoidFunctionContainer(), true);
        ILayer hiddenLayer2 = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(6,6), new SigmoidFunctionContainer(), true);
        ILayer outputLayer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(10,1), new SigmoidFunctionContainer(), true);

        LayerUtils.fullyConnectLayers(inputLayer, hiddenLayer, true);
        LayerUtils.fullyConnectLayers(hiddenLayer, hiddenLayer2, true);
        LayerUtils.fullyConnectLayers(hiddenLayer2, outputLayer, true);

        INeuralNet neuralNet = new NeuralNet();
        INeuralNetCore neuralNetCore = new NeuralNetCore();
        neuralNet.setNeuralNetCore(neuralNetCore);
        neuralNetCore.setInputLayer(inputLayer);
        neuralNetCore.setOutputLayer(outputLayer);
        neuralNetCore.addHiddenLayer(hiddenLayer);
        neuralNetCore.addHiddenLayer(hiddenLayer2);
        NeuralNetMetaData meta = new NeuralNetMetaData();
        meta.setName("28 100 36 10");
        meta.setDescription("daniel " + System.currentTimeMillis());
        neuralNet.setNeuralNetMetaData(meta);
        neuralNetWorker.save(neuralNet);
        return neuralNet;
    }
}