package de.baleipzig.iris.ui.service.base;

import de.baleipzig.iris.logic.worker.INeuralNetWorker;

public interface IBaseSearchNNService extends IBaseService {
    INeuralNetWorker getNeuralNetWorker();
}
