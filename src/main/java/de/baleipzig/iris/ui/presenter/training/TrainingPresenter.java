package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.ui.presenter.base.BasePresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;

@RequiredArgsConstructor
public class TrainingPresenter extends BasePresenter {
    private final ITrainingView view;
    private final ITrainingService service;

    private DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private TrainingsConfiguration trainingsConfiguration;


    @Override
    public void init() {
        initAndBindTrainingConfigurationToView();
    }

    private void initAndBindTrainingConfigurationToView() {
        trainingsConfiguration = new TrainingsConfiguration();
        dozerBeanMapper.map(service.getNeuralNetConfig(), trainingsConfiguration);

        view.bindTrainingsConfiguration(trainingsConfiguration);
    }

    public void startTraining() {
        System.out.println(trainingsConfiguration.getLearningRate());
    }
}
