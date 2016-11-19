package de.baleipzig.iris.ui.view.recognition;

import de.baleipzig.iris.ui.presenter.recognition.RecognitionPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.recognition.RecognitionViewModel;

import java.awt.image.BufferedImage;

public interface IRecognitionView extends IBaseSearchNNView<RecognitionPresenter> {

    String VIEW_NAME = "";

    void setResult(Integer digit);

    void clearResult();

    void addInfoText(String info);

    void updateViewModel(RecognitionViewModel viewModel);

    BufferedImage getImage();
}
