package de.baleipzig.iris.ui.presenter.training;

import de.baleipzig.iris.common.utils.ImageUtils;
import de.baleipzig.iris.enums.ImageType;
import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
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

    public TrainingPresenter(ITrainingView view, ITrainingService service) {
        super(view, service);
    }

    @Override
    public void init() {
        super.init();

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

        IEntityLayerAssembler<BufferedImage> imageConverter = new ImageAssembler();
        IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();
        GradientDescentConfig config = new GradientDescentConfig(3.,trainMapper.size(),5,30);
        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(config);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        INeuralNetTrainer<BufferedImage, Integer> trainer = new MiniBadgeTrainer<>(imageConverter, digitConverter,config, netTrainer, service.getNeuralNetWorker(), nodeTrainer);

        trainer.setNeuralNet(trainingViewModel.getNeuralNet());
        long millis = System.currentTimeMillis();
        trainer.train(trainMapper);
        view.addInfoText("time: " + (System.currentTimeMillis() -millis));
    }

    private void loadNeuralNet() throws Exception {

        UUID selectedNN = trainingViewModel.getSelectedNeuralNetId();

        if( selectedNN == null) {
            throw new Exception("No neural net selected. Please choose a neural net.");
        }

        INeuralNetWorker worker = service.getNeuralNetWorker();

        if(trainingViewModel.getNeuralNet() == null) {

            trainingViewModel.setNeuralNet(worker.load(selectedNN));
            return;
        }

        if(selectedNN.compareTo(trainingViewModel.getNeuralNet().getNeuralNetMetaData().getId()) != 0) {
            trainingViewModel.setNeuralNet(worker.load(selectedNN));
        }
    }
}