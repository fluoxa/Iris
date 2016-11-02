package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;
import org.dozer.DozerBeanMapper;

public class TrainingPresenter extends BaseSearchNNPresenter<ITrainingView, ITrainingService> {

    private DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
    private TrainingViewModel trainingViewModel;

    public TrainingPresenter(ITrainingView view, ITrainingService service) {
        super(view, service);
    }

    @Override
    public void init() {
        super.init();

        trainingViewModel = new TrainingViewModel();
        initViewModel(trainingViewModel);
        bindViewModel(trainingViewModel);
    }

    private void initViewModel(TrainingViewModel trainingViewModel) {

        dozerBeanMapper.map(service.getNeuralNetConfig(), trainingViewModel);
        trainingViewModel.setInfoText("Training Config loaded...\n");
    }

    private void bindViewModel(TrainingViewModel trainingViewModel) {
        view.bindTrainingsConfiguration(trainingViewModel);
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {
        System.out.println("TrainingPresenter " + metaData.getId() + "selected");
    }

    public void startTraining() {
        System.out.println(trainingViewModel.getLearningRate());
    }
}