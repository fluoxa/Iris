package de.baleipzig.iris.ui.presenter.recognition;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.recognition.IRecognitionView;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RecognitionPresenter extends BaseSearchNNPresenter<IRecognitionView, IRecognitionService> {


    private INeuralNet neuralNet;


    public RecognitionPresenter(IRecognitionView view, IRecognitionService service) {
        super(view, service);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {
        System.out.println("RecognitionPresenter " + metaData.getId() + "selected");
        neuralNet = service.getNeuralNetWorker().load(metaData.getId());
    }

    public void processImage(BufferedImage image) {
        BufferedImage scaledImage = Scalr.resize(image, 28);

        if (neuralNet == null) {
            return;
        }

        service.getImageAssembler().copy(scaledImage, neuralNet.getNeuralNetCore().getInputLayer());
        service.getNeuralNetWorker().propagateForward(neuralNet);
        Integer digit = service.getDigitAssembler().convert(neuralNet.getNeuralNetCore().getOutputLayer());

        view.setResult(digit);

        try {
            File outputFile = new File("saved.png");
            ImageIO.write(scaledImage, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}