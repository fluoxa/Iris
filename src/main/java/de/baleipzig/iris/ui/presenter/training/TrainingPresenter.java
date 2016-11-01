package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;
import org.dozer.DozerBeanMapper;


public class TrainingPresenter extends BaseSearchNNPresenter<ITrainingService> {
    private final ITrainingView view;

    private DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private TrainingsConfiguration trainingsConfiguration;


    public TrainingPresenter(ITrainingView view, ITrainingService service) {
        super(service);
        this.view = view;
    }

    @Override
    public void init() {
        super.init();
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