package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.neuralnettrainer.GradientDescent.GradientDescentParams;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
import de.baleipzig.iris.logic.worker.IImageWorker;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;

import java.awt.image.BufferedImage;
import java.util.UUID;

public interface ITrainingService extends IBaseSearchNNService {

    NeuralNetConfig getNeuralNetConfig();
    IImageWorker getImageWorker();
    IEntityLayerAssembler<BufferedImage> getImageAssembler();
    IEntityLayerAssembler<Integer> getDigitAssembler();
    INeuralNetTrainer<BufferedImage, Integer> getGradDescTrainer(GradientDescentParams params);
    INeuralNet loadNeuralNet(UUID selectedNeuralNetId, INeuralNet currentlyLoadedNeuralNet);

    void initViewModel(TrainingViewModel model);
    void bindViewModelToView(TrainingViewModel model, ITrainingView view);
}
