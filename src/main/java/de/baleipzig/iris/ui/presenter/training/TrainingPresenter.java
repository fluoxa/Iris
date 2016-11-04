package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.common.utils.ImageUtils;
import de.baleipzig.iris.enums.ImageType;
import de.baleipzig.iris.logic.neuralnettrainer.GradientDescent.GradientDescentParams;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;

import java.awt.image.BufferedImage;
import java.util.Map;

public class TrainingPresenter extends BaseSearchNNPresenter<ITrainingView, ITrainingService> {


    private TrainingViewModel model = new TrainingViewModel();

    public TrainingPresenter(ITrainingView view, ITrainingService service) {

        super(view, service);
    }

    @Override
    public void init() {

        super.init();
        service.initViewModel(model);
        service.bindViewModelToView(model, view);

        view.addInfoText("Training config loaded...");
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {
        model.setSelectedNeuralNetId(metaData.getId());
    }

    public void startTraining() {

        view.setTrainingLock(true);

        model.setNeuralNet(service.loadNeuralNet(model.getSelectedNeuralNetId(), model.getNeuralNet()));

        if (model.getNeuralNet() == null ){
            view.addInfoText("No Neural Net chosen. Please select a Neural Net.");
            view.setTrainingLock(false);
            return;
        }

        GradientDescentParams params= new GradientDescentParams(
                model.getLearningRate(),
                model.getTrainingSetSize(),
                model.getTrainingCycles(),
                model.getMiniBadgeSize());

        INeuralNetTrainer<BufferedImage, Integer> trainer = service.getGradDescTrainer(params);
        trainer.setNeuralNet(model.getNeuralNet());
        Map<BufferedImage, Integer> trainMapper = ImageUtils.convertToResultMap(service.getImageWorker().loadRandomImagesByType(params.getBadgeSize(), ImageType.TRAIN));
        view.addInfoText(String.format("start training Neural Net: %s", model.getNeuralNet().getNeuralNetMetaData().getName()));

        //trainer.train(trainMapper);
        // hier noch interruptable test rein: service.testNeuralNet(model.getNeuralNet());

        view.setTrainingLock(false);
    }
}