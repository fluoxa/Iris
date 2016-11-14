package de.baleipzig.iris.ui.presenter.training;

import com.vaadin.ui.UI;
import de.baleipzig.iris.common.utils.ImageUtils;
import de.baleipzig.iris.enums.ImageType;
import de.baleipzig.iris.enums.ResultType;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
import de.baleipzig.iris.logic.neuralnettrainer.TrainingProgress;
import de.baleipzig.iris.logic.neuralnettrainer.gradientdescent.*;
import de.baleipzig.iris.logic.neuralnettrainer.result.Result;
import de.baleipzig.iris.logic.neuralnettrainer.result.TestResult;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.neuralnetconfig.INeuralNetConfigView;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TrainingPresenter extends BaseSearchNNPresenter<ITrainingView, ITrainingService> {

    private TrainingViewModel model = new TrainingViewModel();

    private INeuralNetTrainer<BufferedImage, Integer> trainer;
    private Map<BufferedImage, Integer> testData;

    private ScheduledExecutorService progressService = new ScheduledThreadPoolExecutor(1);
    private Future<?> progressHandler;

    public TrainingPresenter(ITrainingView view, ITrainingService service) {

        super(view, service);
    }

    @Override
    public void init() {

        super.init();
        initViewModel();
        bindViewModelToView();

        view.addInfoText(service.getLanguageHandler().getTranslation("training.base.trainingdataloaded"));
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {

        model.setSelectedNeuralNetId(metaData.getId());
    }

    public Void startTraining() {

        view.addInfoText("setting up training environment...");

        loadNeuralNet();

        if (model.getNeuralNet() == null ){
            view.addInfoText(service.getLanguageHandler().getTranslation("training.base.noneuralnetselected"));
            return null;
        }

        initTraining();

        GradientDescentParams params= new GradientDescentParams(
                model.getLearningRate(),
                model.getTrainingSetSize(),
                model.getTrainingCycles(),
                model.getMiniBadgeSize());

        trainer = getGradDescTrainer(params);
        trainer.setNeuralNet(model.getNeuralNet());
        progressHandler = progressService.scheduleAtFixedRate(createProgressThread(trainer), 0, service.getUiConfig().getProgressUpdateInterval(), TimeUnit.MILLISECONDS );

        Map<BufferedImage, Integer> trainingData = ImageUtils.convertToResultMap(service.getImageWorker().loadRandomImagesByType(params.getTrainingSetSize(), ImageType.TRAIN));
        testData = testData == null ? ImageUtils.convertToResultMap(service.getImageWorker().loadAllImagesByType(ImageType.TEST)) : testData;

        view.addInfoText(String.format("Neural Net %s: training started...", model.getNeuralNet().getNeuralNetMetaData().getName()));

        Result trainingResult = trainer.train(trainingData);

        if(trainingResult.getResultType() != ResultType.SUCCESS) {
            UI.getCurrent().access(() -> view.setTrainingLock(false));
            return null;
        }

        view.addInfoText(String.format("Neural Net %s: training finished...", model.getNeuralNet().getNeuralNetMetaData().getName()));
        view.addInfoText(String.format("Neural Net %s: starting tests...", model.getNeuralNet().getNeuralNetMetaData().getName()));

        TestResult testResult = trainer.getTestResult(testData);

        if(testResult.getResultType() == ResultType.SUCCESS) {

            String message = service.getLanguageHandler().getTranslation("training.presenter.errorrate", new Object[] {model.getNeuralNet().getNeuralNetMetaData().getName(), testResult.getErrorRate()});
            view.addInfoText(message);
        }

        terminateTraining();
        return null;
    }

    public Void stopTraining() {

        trainer.interrupt();
        terminateTraining();
        view.addInfoText(String.format("Neural Net %s: training interrupted...", model.getNeuralNet().getNeuralNetMetaData().getName()));
        return null;
    }

    public Void resetNeuralNet() {

        if(model.getSelectedNeuralNetId() == null) {
            return null;
        }

        model.setNeuralNet(service.getNeuralNetWorker().load(model.getSelectedNeuralNetId()));
        view.addInfoText(String.format("Neural Net %s: reset to initial state...", model.getNeuralNet().getNeuralNetMetaData().getName()));
        return null;
    }

    public Void saveNeuralNet() {

        service.getNeuralNetWorker().save(model.getNeuralNet());
        view.addInfoText(String.format("Neural Net %s: saved neural net...", model.getNeuralNet().getNeuralNetMetaData().getName()));
        return null;
    }

    public Void navigateToConfigView() {

        if(model.getSelectedNeuralNetId() != null) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put("uuid", model.getSelectedNeuralNetId().toString());

            return this.navigateToView(INeuralNetConfigView.VIEW_NAME, parameters);
        }
        else {
            return this.navigateToView(INeuralNetConfigView.VIEW_NAME);
        }
    }

    private void terminateTraining() {

        progressHandler.cancel(true);
        model.setCycleProgress(0.);
        model.setOverallTrainingProgress(0.);

        UI.getCurrent().access(() -> {
            view.setTrainingLock(false);
            view.updateTrainingProgress(model);
        });
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
                !model.getSelectedNeuralNetId().equals(model.getNeuralNet().getNeuralNetMetaData().getId())) {

            model.setNeuralNet(service.getNeuralNetWorker().load(model.getSelectedNeuralNetId()));
        }
    }

    private void initViewModel() {

        service.getDozerBeanMapper().map(service.getNeuralNetConfig(), model);
        model.setCycleProgress(0.);
        model.setOverallTrainingProgress(0.);
    }

    private void bindViewModelToView() {

        view.bindTrainingsConfiguration(model);
    }

    private void initTraining() {

        UI.getCurrent().access(() -> view.setTrainingLock(true));
        model.setOverallTrainingProgress(0.);
        model.setCycleProgress(0.);
        view.updateTrainingProgress(model);
    }

    private Runnable createProgressThread(INeuralNetTrainer<BufferedImage, Integer> trainer) {

        return () -> {
            TrainingProgress progress = trainer.getProgress();
            model.setOverallTrainingProgress(progress.getOverallProgress());
            model.setCycleProgress(progress.getCycleProgress());
            view.updateTrainingProgress(model);
        };
    }
}