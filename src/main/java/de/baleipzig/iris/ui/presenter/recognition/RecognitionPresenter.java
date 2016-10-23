package de.baleipzig.iris.ui.presenter.recognition;

import de.baleipzig.iris.ui.presenter.BasePresenter;
import de.baleipzig.iris.ui.service.recognition.RecognitionService;
import de.baleipzig.iris.ui.view.recognition.RecognitionView;

public class RecognitionPresenter extends BasePresenter {

    private final RecognitionView view;
    private final RecognitionService service;

    public RecognitionPresenter(RecognitionView view, RecognitionService service) {
        this.view = view;
        this.service = service;
    }
}
