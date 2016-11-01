package de.baleipzig.iris.ui.presenter.recognition;

import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.recognition.IRecognitionView;

public class RecognitionPresenter extends BaseSearchNNPresenter<IRecognitionService> {

    private final IRecognitionView view;

    public RecognitionPresenter(IRecognitionView view, IRecognitionService service) {
        super(service);
        this.view = view;
    }

    @Override
    public void init() {

    }
}