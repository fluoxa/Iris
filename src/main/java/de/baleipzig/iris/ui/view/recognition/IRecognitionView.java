package de.baleipzig.iris.ui.view.recognition;

import de.baleipzig.iris.ui.presenter.recognition.RecognitionPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;

public interface IRecognitionView extends IBaseSearchNNView<RecognitionPresenter> {

    String VIEW_NAME = "";

    void setResult(Integer digit);
}
