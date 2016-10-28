package de.baleipzig.iris.ui.presenter.TrainingPresenter;

import de.baleipzig.iris.ui.presenter.BasePresenter;
import lombok.Data;

public class TrainingPresenter extends BasePresenter {

    @Data
    public class ViewData {

        private double learningRate;
        private int miniBadgeSize;
        private int trainingSetSize;
        private int trainingCycles;
    }

    public void startTraining(){


    }
}
