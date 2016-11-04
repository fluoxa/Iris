package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.neuralnettrainer.GradientDescent.*;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
import de.baleipzig.iris.logic.worker.IImageWorker;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingService implements ITrainingService {

    private final INeuralNetWorker neuralNetWorker;
    private final NeuralNetConfig neuralNetConfig;
    private final IImageWorker imageWorker;

    private final IEntityLayerAssembler<BufferedImage> imageAssembler;
    private final IEntityLayerAssembler<Integer> digitAssembler;

    private final DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    @Override
    public INeuralNetTrainer<BufferedImage, Integer> getGradDescTrainer(GradientDescentParams params) {

        GradientDescentConfig<BufferedImage, Integer> trainingConfig = new GradientDescentConfig<>();

        IMiniBadgeNodeTrainer nodeTrainer = new MiniBadgeNodeTrainer(params);
        IGradientDescentLayerTrainer layerTrainer = new GradientDescentLayerTrainer(nodeTrainer);
        IGradientDescentNeuralNetTrainer netTrainer = new GradientDescentNeuralNetTrainer(layerTrainer);

        trainingConfig.setInputConverter(this.getImageAssembler());
        trainingConfig.setOutputConverter(this.getDigitAssembler());
        trainingConfig.setNeuralNetTrainingWorker(netTrainer);
        trainingConfig.setNeuralNetWorker(this.getNeuralNetWorker());
        trainingConfig.setNodeTrainer(nodeTrainer);
        trainingConfig.setParams(params);

        return new MiniBadgeTrainer<>(trainingConfig);
    }

    @Override
    public INeuralNet loadNeuralNet(UUID selectedNeuralNetId, INeuralNet currentlyLoadedNeuralNet) {

        if( selectedNeuralNetId == null) {
            return null;
        }

        if( currentlyLoadedNeuralNet == null ||
            selectedNeuralNetId.compareTo(currentlyLoadedNeuralNet.getNeuralNetMetaData().getId()) != 0) {

            return this.getNeuralNetWorker().load(selectedNeuralNetId);
        }

        return currentlyLoadedNeuralNet;
    }

    @Override
    public void initViewModel(TrainingViewModel model) {

        dozerBeanMapper.map(this.getNeuralNetConfig(), model);
    }

    @Override
    public void bindViewModelToView(TrainingViewModel model, ITrainingView view) {
        view.bindTrainingsConfiguration(model);
    }

}