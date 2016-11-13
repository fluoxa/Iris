package de.baleipzig.iris.ui.view.training;

import de.baleipzig.iris.ui.presenter.training.TrainingPresenter;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;

public interface ITrainingView extends IBaseSearchNNView<TrainingPresenter> {

    String VIEW_NAME = "training";

    void bindTrainingsConfiguration(TrainingViewModel trainingViewModel);
    void addInfoText(String message);
    void setTrainingLock(boolean isLocked);
    void updateTrainingProgress(TrainingViewModel trainingViewModel);
}
