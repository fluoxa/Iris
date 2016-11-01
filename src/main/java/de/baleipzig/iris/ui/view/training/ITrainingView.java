package de.baleipzig.iris.ui.view.training;


import de.baleipzig.iris.ui.presenter.base.BasePresenter;
import de.baleipzig.iris.ui.view.base.IBaseView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;

public interface ITrainingView<P extends BasePresenter> extends IBaseView<P> {

    void bindTrainingsConfiguration(TrainingsConfiguration trainingsConfiguration);
}
