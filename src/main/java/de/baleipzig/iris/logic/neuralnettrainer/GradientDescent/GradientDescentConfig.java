package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import de.baleipzig.iris.logic.converter.neuralnet.IAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradientDescentConfig<InputType, OutputType> {

    private IEntityLayerAssembler<InputType> inputConverter;
    private IAssembler<OutputType> outputConverter;
    private GradientDescentParams params;
    private IGradientDescentNeuralNetTrainer neuralNetTrainingWorker;
    private INeuralNetWorker neuralNetWorker;
    private IMiniBadgeNodeTrainer nodeTrainer;
}