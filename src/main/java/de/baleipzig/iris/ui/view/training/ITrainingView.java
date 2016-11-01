package de.baleipzig.iris.ui.view.training;


import de.baleipzig.iris.ui.presenter.training.TrainingPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;

public interface ITrainingView extends IBaseSearchNNView<TrainingPresenter> {

    void bindTrainingsConfiguration(TrainingsConfiguration trainingsConfiguration);
}
