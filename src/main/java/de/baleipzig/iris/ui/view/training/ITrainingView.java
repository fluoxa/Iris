package de.baleipzig.iris.ui.view.training;


import de.baleipzig.iris.ui.view.IBaseView;

public interface ITrainingView  extends IBaseView {

    void setLearningRate(double learningRate);
    void setMiniBadgeSize(int miniBadgeSize);
    void setTrainingSetSize(int size);
    void setTrainingCycles(int size);


}
