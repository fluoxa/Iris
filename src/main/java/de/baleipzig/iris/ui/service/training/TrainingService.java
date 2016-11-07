package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingService implements ITrainingService {

    private final INeuralNetWorker neuralNetWorker;
    private final NeuralNetConfig neuralNetConfig;
    private final IImageWorker imageWorker;

    private final IEntityLayerAssembler<BufferedImage> imageAssembler;
    private final IAssembler<Integer> digitAssembler;
    private final DozerBeanMapper dozerBeanMapper;
    private final LanguageHandler languageHandler;



}
