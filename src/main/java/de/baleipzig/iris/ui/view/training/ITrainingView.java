package de.baleipzig.iris.ui.view.training;


import de.baleipzig.iris.ui.presenter.TrainingPresenter.TrainingPresenter;
import de.baleipzig.iris.ui.view.IBaseView;

public interface ITrainingView  extends IBaseView {

    void bindViewData(TrainingPresenter.ViewData viewData);
}
