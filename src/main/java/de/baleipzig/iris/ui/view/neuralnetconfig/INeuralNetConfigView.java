package de.baleipzig.iris.ui.view.neuralnetconfig;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.ui.presenter.neuralnetconfig.NeuralNetConfigPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.neuralnetconfig.NeuralNetConfigViewModel;

import java.util.List;

public interface INeuralNetConfigView extends IBaseSearchNNView<NeuralNetConfigPresenter> {

    String VIEW_NAME = "neuralnetconfig";

    void bindTrainingsConfiguration(NeuralNetConfigViewModel trainingViewModel);
    void update(NeuralNetConfigViewModel model);
    void unselectSearchList();
    void resetView();
    List<Dimension> getHiddenLayerDimensions();
}
