package de.baleipzig.iris.ui.presenter.recognition;

import de.baleipzig.iris.ui.presenter.BasePresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.recognition.IRecognitionView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecognitionPresenter extends BasePresenter {

    private final IRecognitionView view;
    private final IRecognitionService service;
}