package de.baleipzig.iris.ui.presenter.recognition;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.recognition.IRecognitionView;

public class RecognitionPresenter extends BaseSearchNNPresenter<IRecognitionView, IRecognitionService> {

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
    }
}