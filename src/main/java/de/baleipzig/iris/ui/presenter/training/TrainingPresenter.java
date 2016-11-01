package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;
import org.dozer.DozerBeanMapper;


public class TrainingPresenter extends BaseSearchNNPresenter<ITrainingView, ITrainingService> {
    private DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private TrainingsConfiguration trainingsConfiguration;

    public TrainingPresenter(ITrainingView view, ITrainingService service) {
        super(view, service);
    }

    @Override
    public void init() {
        super.init();
        initAndBindTrainingConfigurationToView();
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {
        System.out.println("TrainingPresenter " + metaData.getId() + "selected");
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
