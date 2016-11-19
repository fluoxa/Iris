package de.baleipzig.iris.ui.service.recognition;

import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import org.dozer.DozerBeanMapper;

public interface IRecognitionService extends IBaseSearchNNService {
    ImageAssembler getImageAssembler();

    DigitAssembler getDigitAssembler();
    INeuralNetWorker getNeuralNetWorker();

    DozerBeanMapper getBeanMapper();
}
