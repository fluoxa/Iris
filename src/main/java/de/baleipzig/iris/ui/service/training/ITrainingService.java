package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.configuration.UiConfig;
import de.baleipzig.iris.logic.converter.neuralnet.IAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.worker.IImageWorker;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import org.dozer.DozerBeanMapper;

import java.awt.image.BufferedImage;

public interface ITrainingService extends IBaseSearchNNService {

    NeuralNetConfig getNeuralNetConfig();
    UiConfig getUiConfig();
    IImageWorker getImageWorker();
    IEntityLayerAssembler<BufferedImage> getImageAssembler();
    IAssembler<Integer> getDigitAssembler();

    DozerBeanMapper getDozerBeanMapper();
}
