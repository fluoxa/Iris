package de.baleipzig.iris.ui.service.neuralnetconfig;

import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;

public interface INeuralNetConfigService extends IBaseSearchNNService {

    INeuralNetWorker getNeuralNetWorker();
}
