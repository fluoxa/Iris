package de.baleipzig.iris.ui.view.neuralnetconfig;

import de.baleipzig.iris.ui.presenter.neuralnetconfig.NeuralNetConfigPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.neuralnetconfig.NeuralNetConfigViewModel;

public interface INeuralNetConfigView extends IBaseSearchNNView<NeuralNetConfigPresenter> {

    void bindTrainingsConfiguration(NeuralNetConfigViewModel trainingViewModel);
    void updateNeuralNetStructure(String message);
}