package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.common.utils.ImageUtils;
import de.baleipzig.iris.enums.ImageType;
import de.baleipzig.iris.logic.neuralnettrainer.GradientDescent.*;
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

    private INeuralNetTrainer<BufferedImage, Integer> trainer;
    private Map<BufferedImage, Integer> testData;

    public TrainingPresenter(ITrainingView view, ITrainingService service) {

        super(view, service);
    }

    @Override
    public void init() {

        super.init();
        initViewModel();
        bindViewModelToView();

        view.addInfoText("Training config loaded...");
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {

        model.setSelectedNeuralNetId(metaData.getId());
    }

    public void startTraining() {

        view.setTrainingLock(true);

        loadNeuralNet();

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

        trainer = getGradDescTrainer(params);
        trainer.setNeuralNet(model.getNeuralNet());
        Map<BufferedImage, Integer> trainingData = ImageUtils.convertToResultMap(service.getImageWorker().loadRandomImagesByType(params.getBadgeSize(), ImageType.TRAIN));
        testData = testData == null ? ImageUtils.convertToResultMap(service.getImageWorker().loadAllImagesByType(ImageType.TEST)) : testData;

        view.addInfoText(String.format("Neural Net %s: training started...", model.getNeuralNet().getNeuralNetMetaData().getName()));
        trainer.train(trainingData);
        view.addInfoText(String.format("Neural Net %s: training finished...", model.getNeuralNet().getNeuralNetMetaData().getName()));

        view.addInfoText(String.format("Neural Net %s: starting tests...", model.getNeuralNet().getNeuralNetMetaData().getName()));
        double errorRate = trainer.getErrorRate(testData);
        view.addInfoText(String.format("Neural Net %s: error rate %4f", model.getNeuralNet().getNeuralNetMetaData().getName(), errorRate));

        view.setTrainingLock(false);
    }

    private INeuralNetTrainer<BufferedImage, Integer> getGradDescTrainer(GradientDescentParams params) {

        GradientDescentConfig<BufferedImage, Integer> trainingConfig = new GradientDescentConfig<>();

        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(params);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        trainingConfig.setInputConverter(service.getImageAssembler());
        trainingConfig.setOutputConverter(service.getDigitAssembler());
        trainingConfig.setNeuralNetTrainingWorker(netTrainer);
        trainingConfig.setNeuralNetWorker(service.getNeuralNetWorker());
        trainingConfig.setNodeTrainer(nodeTrainer);
        trainingConfig.setParams(params);

        return new MiniBadgeTrainer<>(trainingConfig);
    }

    private void loadNeuralNet() {

        if( model.getSelectedNeuralNetId() == null) {
            return;
        }

        if( model.getNeuralNet() == null ||
                model.getSelectedNeuralNetId().compareTo(model.getNeuralNet().getNeuralNetMetaData().getId()) != 0) {

            model.setNeuralNet(service.getNeuralNetWorker().load(model.getSelectedNeuralNetId()));
        }
    }

    private void initViewModel() {

        service.getDozerBeanMapper().map(service.getNeuralNetConfig(), model);
    }

    private void bindViewModelToView() {

        view.bindTrainingsConfiguration(model);
    }
}