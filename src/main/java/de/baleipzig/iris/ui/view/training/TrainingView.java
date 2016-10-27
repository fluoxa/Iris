package de.baleipzig.iris.ui.view.training;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import de.baleipzig.iris.ui.presenter.TrainingPresenter.TrainingPresenter;
import de.baleipzig.iris.ui.view.BaseView;
import lombok.Setter;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "trainingNeuralNet")
@Setter
public class TrainingView extends BaseView<TrainingPresenter> {

    //region -- member --

    public static final String VIEW_NAME = "trainingNeuralNet";

    private double learningRate;
    private int miniBadgeSize;
    private int trainingSetSize;
    private int trainingCycles;

    //endregion

    // region -- methods --

    @PostConstruct
    void init() {

        Label learningRateLabel = new Label("Learning Rate");
        TextField learningRateField = new TextField("", Double.toString(learningRate));
        this.addComponent(learningRateLabel);
        this.addComponent(learningRateField);

        Button startTraining = new Button("start Training");
        startTraining.addClickListener(e -> presenter.startTraining());

        this.addComponent(startTraining);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


    }

    //endregion
}
