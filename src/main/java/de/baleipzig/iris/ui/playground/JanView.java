package de.baleipzig.iris.ui.playground;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.utils.ImageUtils;
import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.enums.ImageType;
import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ILayerEntityAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
import de.baleipzig.iris.logic.neuralnettrainer.GradientDescent.*;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
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
import java.util.UUID;


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
    private Button trainButton = new Button("train hard");
    private Button breakButton = new Button("Take a break");
    private Label resultLabel = new Label();
    private Label oldResultLabel = new Label();
    private Label iterationLabel = new Label();

    @PostConstruct
    void init() {
        this.addComponent(new Label("Jans Spielwiese"));
        this.addComponent(trainButton);
        this.addComponent(breakButton);
        this.addComponent(resultLabel);
        this.addComponent(oldResultLabel);
        trainButton.addClickListener(event -> {

            testMapper = null;
            trainMapper = null;

            if(train == false) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        train = true;
                        while(train) {
                            trainAndSaveNeuralNet("eac2abd8-f993-41f8-8b74-6712521eef3a");
                            oldResultLabel.setValue("old: " + resultLabel.getValue());
                            resultLabel.setValue("new: " +testNeuralNet("eac2abd8-f993-41f8-8b74-6712521eef3a"));
                        }
                    }
                };
                (new Thread(r)).start();
            }

        });
        breakButton.addClickListener(e -> {
            train = false;
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

//        imageWorker.exportImageToDb();
//        trainAndSaveNeuralNet("ba48995a-e781-4d26-8207-f5b6ad4b4de2");
//        testNeuralNet("ba48995a-e781-4d26-8207-f5b6ad4b4de2");
    }

    private String testNeuralNet(String id) {
        numberOfCorrectPredictions = 0;
        IEntityLayerAssembler<BufferedImage> imageAssembler = new ImageAssembler();
        ILayerEntityAssembler<Integer> digitAssembler = new DigitAssembler();
        INeuralNet neuralNet = neuralNetWorker.load(UUID.fromString(id));

        if(testMapper == null) {
            testMapper = ImageUtils.convertToResultMap(imageWorker.loadRandomImagesByType(10000,ImageType.TEST));
        }


        testMapper.forEach((image, expectedDigit) -> {

            imageAssembler.copy(image, neuralNet.getNeuralNetCore().getInputLayer());

            neuralNetWorker.propagateForward(neuralNet);

            int result = digitAssembler.convert(neuralNet.getNeuralNetCore().getOutputLayer());

            if(expectedDigit == result){
                numberOfCorrectPredictions++;
            }
        });

        String result = "Richtige Ergebnisse: " + (100.*numberOfCorrectPredictions/ testMapper.size()) + "%";
        System.out.println(result);
        System.out.println(neuralNet);
        return result;
    }

    private void trainAndSaveNeuralNet() {

        INeuralNet neuralNet = createNeuralNet();

        Map<BufferedImage, Integer> resultMapper = ImageUtils.convertToResultMap(imageWorker.loadRandomImagesByType( 2500, ImageType.TRAIN));

        IEntityLayerAssembler<BufferedImage> imageConverter = new ImageAssembler();
        IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();
        GradientDescentConfig config = new GradientDescentConfig(2.,resultMapper.size(),1,50);
        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(config);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        INeuralNetTrainer<BufferedImage, Integer> trainer = new MiniBadgeTrainer<>(imageConverter, digitConverter,config, netTrainer, neuralNetWorker, nodeTrainer);

        trainer.setNeuralNet(neuralNet);
        trainer.train(resultMapper);

        INeuralNet trainedNet = trainer.getNeuralNet();
        trainedNet.getNeuralNetMetaData().setId(UUID.randomUUID());
        trainedNet.getNeuralNetMetaData().setDescription("trainedNet");

        neuralNetWorker.save(trainedNet);
    }

    private void trainAndSaveNeuralNet(String id) {

        INeuralNet neuralNet = neuralNetWorker.load(UUID.fromString(id));

        trainMapper = ImageUtils.convertToResultMap(imageWorker.loadRandomImagesByType(5000,ImageType.TRAIN));

        IEntityLayerAssembler<BufferedImage> imageConverter = new ImageAssembler();
        IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();
        GradientDescentConfig config = new GradientDescentConfig(3.,trainMapper.size(),5,30);
        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(config);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        INeuralNetTrainer<BufferedImage, Integer> trainer = new MiniBadgeTrainer<>(imageConverter, digitConverter,config, netTrainer, neuralNetWorker, nodeTrainer);

        trainer.setNeuralNet(neuralNet);
        long millis = System.currentTimeMillis();
        trainer.train(trainMapper);
        System.out.println("Dauer: " + (System.currentTimeMillis() -millis));
        neuralNetWorker.save(trainer.getNeuralNet());
    }

    private INeuralNet createNeuralNet(){

        ILayer inputLayer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(28,28), null, false);
        ILayer hiddenLayer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(5,6), new SigmoidFunctionContainer(), true);
        ILayer outputLayer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(10,1), new SigmoidFunctionContainer(), true);

        LayerUtils.fullyConnectLayers(inputLayer, hiddenLayer, true);
        LayerUtils.fullyConnectLayers(hiddenLayer, outputLayer, true);

        INeuralNet neuralNet = new NeuralNet();
        INeuralNetCore neuralNetCore = new NeuralNetCore();
        neuralNet.setNeuralNetCore(neuralNetCore);
        neuralNetCore.setInputLayer(inputLayer);
        neuralNetCore.setOutputLayer(outputLayer);
        neuralNetCore.addHiddenLayer(hiddenLayer);
        neuralNet.setNeuralNetMetaData(new NeuralNetMetaData());

        return neuralNet;
    }
}