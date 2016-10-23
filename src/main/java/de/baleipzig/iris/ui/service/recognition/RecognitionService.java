package de.baleipzig.iris.ui.service.recognition;

import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionService implements IRecognitionService {
    private final INeuralNetWorker neuralNetWorker;
}
