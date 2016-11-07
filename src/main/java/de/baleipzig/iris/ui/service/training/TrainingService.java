package de.baleipzig.iris.ui.service.training;

import com.vaadin.spring.annotation.UIScope;
import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.converter.neuralnet.IAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.worker.IImageWorker;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.language.LanguageHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

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
