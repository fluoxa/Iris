package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;

public interface ITrainingService extends IBaseSearchNNService {

    NeuralNetConfig getNeuralNetConfig();
}
