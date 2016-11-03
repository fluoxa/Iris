package de.baleipzig.iris.logic.neuralnettrainer.GradientDescent;

import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.neuralnettrainer.INeuralNetTrainer;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import lombok.Getter;

import java.util.Map;

public class MiniBadgeTrainer<InputType, OutputType>
        implements INeuralNetTrainer<InputType, OutputType> {

    //region -- member --

    @Getter
    private INeuralNet neuralNet = null;

    private final IEntityLayerAssembler<InputType> inputConverter;
    private final IEntityLayerAssembler<OutputType> outputConverter;
    private final GradientDescentParams params;
    private final IGradientDescentNeuralNetTrainer neuralNetTrainingWorker;
    private final INeuralNetWorker neuralNetWorker;
    private final IMiniBadgeNodeTrainer nodeTrainer;

    private boolean isInterrupted;

    //endregion

    //region -- constructor --

    public MiniBadgeTrainer(GradientDescentConfig<InputType, OutputType> config) {

        this.inputConverter = config.getInputConverter();
        this.outputConverter = config.getOutputConverter();
        this.params = config.getParams();
        this.neuralNetTrainingWorker = config.getNeuralNetTrainingWorker();
        this.neuralNetWorker = config.getNeuralNetWorker();

        this.nodeTrainer = config.getNodeTrainer();
    }

    //endregion

    //region -- methods --

    public void setNeuralNet(INeuralNet neuralNet) {

        this.neuralNet = neuralNet;
        this.nodeTrainer.updateBiasWeightCache(neuralNet);
    }

    public void train(Map<InputType, OutputType> trainingData) {

        isInterrupted = false;

        int cycle = 0;

        while( cycle < params.getTrainingCycles() && !isInterrupted){

            System.out.print(cycle + " ");

            trainingData.forEach( (inputData, expectedResult) -> {

                inputConverter.copy(inputData, neuralNet.getNeuralNetCore().getInputLayer());
                neuralNetWorker.propagateForward(neuralNet);

                ILayer expectedResultLayer = outputConverter.convert(expectedResult, null);
                neuralNetTrainingWorker.propagateBackward(neuralNet, expectedResultLayer);
            });

            cycle++;
        }

        isInterrupted = false;
    }

    public void interruptTraining() {
        isInterrupted = true;
    }

    //endregion
}