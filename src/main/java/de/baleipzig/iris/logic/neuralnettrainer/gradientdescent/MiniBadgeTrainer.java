package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.enums.ResultType;
import de.baleipzig.iris.logic.neuralnettrainer.BaseTrainer;
import de.baleipzig.iris.logic.neuralnettrainer.result.Result;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniBadgeTrainer<InputType, OutputType>
        extends BaseTrainer<InputType, OutputType> {

    //region -- member --

    private final GradientDescentParams params;
    private final IGradientDescentNeuralNetTrainer neuralNetTrainingWorker;
    private final IMiniBadgeNodeTrainer nodeTrainer;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

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

        progress.setCycleProgress(0.);
        progress.setOverallProgress(0.);

        int cycle = 0;
        int trainingCycles = params.getTrainingCycles();
        int trainingRun = 0;
        int cycleLength = params.getTrainingSetSize();
        int stepSize = cycleLength > 100 ? cycleLength/100 : 1;

        while( cycle < trainingCycles) {

            progress.setCycleProgress(0.);
            progress.setOverallProgress(((double) cycle)/trainingCycles);

            for(Map.Entry<InputType, OutputType> trainingDatum : trainingData.entrySet()) {

                if( interrupted ) {

                    interrupted = false;
                    return new Result(ResultType.FAILURE);
                }

                trainingRun++;

                if( trainingRun % stepSize == 0) {
                    progress.setCycleProgress((double) trainingRun /cycleLength);
                }

                inputConverter.copy(trainingDatum.getKey(), neuralNet.getNeuralNetCore().getInputLayer());
                neuralNetWorker.propagateForward(neuralNet);

                ILayer expectedResultLayer = outputConverter.convert(trainingDatum.getValue(), null);
                neuralNetTrainingWorker.propagateBackward(neuralNet, expectedResultLayer);
            }

            progress.setCycleProgress(1.);
            cycle++;
            trainingRun = 0;
        }

        progress.setOverallProgress(1.);
        interrupted = false;
        return new Result(ResultType.SUCCESS);
    }

    //endregion
}