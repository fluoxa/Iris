package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;

public interface ITrainingService {

    NeuralNetConfig getNeuralNetConfig();

    INeuralNetWorker getNeuralNetWorker();
}
