package de.baleipzig.iris.ui.presenter.recognition;

import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.recognition.IRecognitionView;
import de.baleipzig.iris.ui.viewmodel.recognition.RecognitionViewModel;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RecognitionPresenter extends BaseSearchNNPresenter<IRecognitionView, IRecognitionService> {

    private INeuralNet neuralNet;

    private RecognitionViewModel viewModel = new RecognitionViewModel();

    public RecognitionPresenter(IRecognitionView view, IRecognitionService service) {
        super(view, service);
    }

    @Override
    public void init() {
        super.init();
        view.updateViewModel(viewModel);
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {
        viewModel = new RecognitionViewModel();
        neuralNet = null;

        if(metaData != null) {
            neuralNet = service.getNeuralNetWorker().load(metaData.getId());
            service.getBeanMapper().map(neuralNet.getNeuralNetMetaData(), viewModel);
            view.addInfoText("neural net " + neuralNet.getNeuralNetMetaData().getName() + " loaded");
        }   else {
            view.addInfoText("unloaded net");
        }

        view.updateViewModel(viewModel);
        processImage(view.getImage());
    }

    public void processImage(BufferedImage image) {
        BufferedImage scaledImage = Scalr.resize(image, 28);

        if (neuralNet == null) {
            view.clearResult();
            return;
        }

        service.getImageAssembler().copy(scaledImage, neuralNet.getNeuralNetCore().getInputLayer());
        service.getNeuralNetWorker().propagateForward(neuralNet);
        Integer digit = service.getDigitAssembler().convert(neuralNet.getNeuralNetCore().getOutputLayer());

        for(int x = 0; x < 10; x++ ) {
            System.out.printf("%1.2f ", neuralNet.getNeuralNetCore().getOutputLayer().getNode(x, 0).getActivation());
        }
        System.out.println("");

        view.setResult(digit);

        try {
            File outputFile = new File("saved.png");
            ImageIO.write(scaledImage, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}