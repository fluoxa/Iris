package de.baleipzig.iris.ui.service.recognition;

import de.baleipzig.iris.logic.worker.INeuralNetWorker;

public interface IRecognitionService {
    INeuralNetWorker getNeuralNetWorker();
}
