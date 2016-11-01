package de.baleipzig.iris.ui.service.recognition;

import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;

public interface IRecognitionService extends IBaseSearchNNService {
    INeuralNetWorker getNeuralNetWorker();
}
