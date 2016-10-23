package de.baleipzig.iris.ui.service.recognition;

import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.service.IService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionService implements IService {
    private final INeuralNetWorker neuralNetWorker;
}
