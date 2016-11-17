package de.baleipzig.iris.ui.view.recognition;

import de.baleipzig.iris.ui.presenter.recognition.RecognitionPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.recognition.RecognitionViewModel;

public interface IRecognitionView extends IBaseSearchNNView<RecognitionPresenter> {

    String VIEW_NAME = "";

    void setResult(Integer digit);

    void addInfoText(String info);

    void bindViewModel(RecognitionViewModel viewModel);
}
