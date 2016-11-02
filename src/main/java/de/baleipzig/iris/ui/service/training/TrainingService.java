package de.baleipzig.iris.ui.service.training;

import de.baleipzig.iris.configuration.NeuralNetConfig;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingService implements ITrainingService {

    private final INeuralNetWorker neuralNetWorker;
    private final NeuralNetConfig neuralNetConfig;
}
