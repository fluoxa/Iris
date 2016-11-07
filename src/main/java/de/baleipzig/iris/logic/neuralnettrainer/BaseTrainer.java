package de.baleipzig.iris.logic.neuralnettrainer;

import de.baleipzig.iris.logic.converter.neuralnet.IAssembler;
import de.baleipzig.iris.logic.converter.neuralnet.IEntityLayerAssembler;
import de.baleipzig.iris.logic.neuralnettrainer.result.TestResult;
import de.baleipzig.iris.logic.worker.INeuralNetWorker;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import lombok.Getter;

import java.util.Map;

public abstract class BaseTrainer<InputType, OutputType> implements INeuralNetTrainer<InputType, OutputType> {

    //region -- member --

    @Getter
    protected INeuralNet neuralNet = null;

    protected final IEntityLayerAssembler<InputType> inputConverter;
    protected final IAssembler<OutputType> outputConverter;
    protected final INeuralNetWorker neuralNetWorker;

    protected boolean interrupted;

    //endregion

    //region -- constructor --

    public BaseTrainer (IEntityLayerAssembler<InputType> inputConverter, IAssembler<OutputType> outputConverter, INeuralNetWorker neuralNetWorker) {

        this.inputConverter = inputConverter;
        this.outputConverter = outputConverter;
        this.neuralNetWorker = neuralNetWorker;
    }

    //endregion

    //region -- methods --

    public TestResult getTestResult(Map<InputType, OutputType> testData) {

        int numberOfCorrectPredictions = 0;
        interrupted = false;

        for(Map.Entry<InputType, OutputType> testEntity : testData.entrySet()) {

            if(interrupted) {
                return new TestResult(false, -1.);
            }

            inputConverter.copy(testEntity.getKey(), neuralNet.getNeuralNetCore().getInputLayer());

            neuralNetWorker.propagateForward(neuralNet);

            OutputType result = outputConverter.convert(neuralNet.getNeuralNetCore().getOutputLayer());

            if(testEntity.getValue() == result){
                numberOfCorrectPredictions++;
            }
        }

        return new TestResult(true, 1. - ((double) numberOfCorrectPredictions / (double) testData.size()));
    }

    public void interruptTest() {
        interrupted = true;
    }

    //endregion
}