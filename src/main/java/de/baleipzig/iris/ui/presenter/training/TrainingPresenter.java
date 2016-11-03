package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.common.utils.ImageUtils;
import de.baleipzig.iris.enums.ImageType;
import de.baleipzig.iris.logic.neuralnettrainer.GradientDescent.*;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;
import org.dozer.DozerBeanMapper;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

public class TrainingPresenter extends BaseSearchNNPresenter<ITrainingView, ITrainingService> {

    private DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
    private TrainingViewModel trainingViewModel = new TrainingViewModel();
    private GradientDescentConfig<BufferedImage, Integer> trainingConfig;
    private GradientDescentParams gradientDescentParams;

    public TrainingPresenter(ITrainingView view, ITrainingService service) {

        super(view, service);
    }

    @Override
    public void init() {
        super.init();

        setupTrainerConfig();
        initViewModel(trainingViewModel);
        bindViewModel(trainingViewModel);

        view.addInfoText("Training config loaded...");
    }

    private void initViewModel(TrainingViewModel trainingViewModel) {

        dozerBeanMapper.map(service.getNeuralNetConfig(), trainingViewModel);
    }

    private void bindViewModel(TrainingViewModel trainingViewModel) {
        view.bindTrainingsConfiguration(trainingViewModel);
    }

    private void setupTrainerConfig() {

        trainingConfig = new GradientDescentConfig<>();

        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(gradientDescentParams);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        trainingConfig.setInputConverter(service.getImageAssembler());
        trainingConfig.setOutputConverter(service.getDigitAssembler());
        trainingConfig.setNeuralNetTrainingWorker(netTrainer);
        trainingConfig.setNeuralNetWorker(service.getNeuralNetWorker());
        trainingConfig.setNodeTrainer(nodeTrainer);
        trainingConfig.setParams(gradientDescentParams);
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {
        trainingViewModel.setSelectedNeuralNetId(metaData.getId());
    }

    public void startTraining() {

        //lock neural net selection and so on
        try{
            loadNeuralNet();
        }
        catch(Exception ex){
            view.addInfoText(ex.getMessage());
            //unlock neural net selection and so on
            return;
        }

        Map<BufferedImage, Integer> trainMapper = ImageUtils.convertToResultMap(service.getImageWorker().loadRandomImagesByType(5000, ImageType.TRAIN));

        gradientDescentParams = new GradientDescentParams(3.,trainMapper.size(),1,3);
        trainingConfig.setParams(gradientDescentParams);

        INeuralNetTrainer<BufferedImage, Integer> trainer = new MiniBadgeTrainer<>(trainingConfig);

        view.addInfoText("start training...");

        trainer.setNeuralNet(trainingViewModel.getNeuralNet());
        long millis = System.currentTimeMillis();
//        trainer.train(trainMapper);
        view.addInfoText("time: " + (System.currentTimeMillis() -millis));
    }

    private void loadNeuralNet() throws Exception {

        UUID selectedNN = trainingViewModel.getSelectedNeuralNetId();

        if( selectedNN == null) {
            throw new Exception("No neural net selected. Please choose a neural net.");
        }

        INeuralNetWorker worker = service.getNeuralNetWorker();

        if(trainingViewModel.getNeuralNet() == null) {

            INeuralNet loadedNet = worker.load(selectedNN);

            trainingViewModel.setNeuralNet(loadedNet);
            return;
        }

        if(selectedNN.compareTo(trainingViewModel.getNeuralNet().getNeuralNetMetaData().getId()) != 0) {
            trainingViewModel.setNeuralNet(worker.load(selectedNN));
        }
    }
}