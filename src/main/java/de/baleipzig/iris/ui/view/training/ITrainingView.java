package de.baleipzig.iris.ui.view.training;


import de.baleipzig.iris.ui.view.IBaseView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;

public interface ITrainingView  extends IBaseView {

    void bindTrainingsConfiguration(TrainingsConfiguration trainingsConfiguration);
}
