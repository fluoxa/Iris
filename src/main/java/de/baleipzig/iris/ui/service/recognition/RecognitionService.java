package de.baleipzig.iris.ui.service.recognition;

import com.vaadin.spring.annotation.UIScope;
import de.baleipzig.iris.logic.converter.neuralnet.DigitAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.ImageAssembler;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.ui.language.LanguageHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionService implements IRecognitionService {

    private final INeuralNetWorker neuralNetWorker;
    private final LanguageHandler languageHandler;
    private final ImageAssembler imageAssembler;
    private final DigitAssembler digitAssembler;
    private final DozerBeanMapper beanMapper;
}
