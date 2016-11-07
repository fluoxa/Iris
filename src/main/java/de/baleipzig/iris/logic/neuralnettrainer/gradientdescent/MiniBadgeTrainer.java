package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.logic.neuralnettrainer.result.Result;
import de.baleipzig.iris.logic.neuralnettrainer.BaseTrainer;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

import java.util.Map;

public class MiniBadgeTrainer<InputType, OutputType>
        extends BaseTrainer<InputType, OutputType> {

    //region -- member --

    private final GradientDescentParams params;
    private final IGradientDescentNeuralNetTrainer neuralNetTrainingWorker;
    private final IMiniBadgeNodeTrainer nodeTrainer;

    //endregion

    //region -- constructor --

    public MiniBadgeTrainer(GradientDescentConfig<InputType, OutputType> config) {

        super(config.getInputConverter(), config.getOutputConverter(), config.getNeuralNetWorker());

        this.neuralNetTrainingWorker = config.getNeuralNetTrainingWorker();
        this.params = config.getParams();
        this.nodeTrainer = config.getNodeTrainer();
    }

    //endregion

    //region -- methods --

    public void setNeuralNet(INeuralNet neuralNet) {

        this.neuralNet = neuralNet;
        this.nodeTrainer.updateBiasWeightCache(neuralNet);
    }

    public Result train(Map<InputType, OutputType> trainingData) {

        interrupted = false;

        int cycle = 0;

        while( cycle < params.getTrainingCycles()){

            if( interrupted ) {
                return new Result(false);
            }

            trainingData.forEach( (inputData, expectedResult) -> {

                inputConverter.copy(inputData, neuralNet.getNeuralNetCore().getInputLayer());
                neuralNetWorker.propagateForward(neuralNet);

                ILayer expectedResultLayer = outputConverter.convert(expectedResult, null);
                neuralNetTrainingWorker.propagateBackward(neuralNet, expectedResultLayer);
            });

            cycle++;
        }

        interrupted = false;
        return new Result(true);
    }

    public void interruptTraining() {
        interrupted = true;
    }

    //endregion
}