package de.baleipzig.iris.logic.neuralnettrainer.gradientdescent;

import de.baleipzig.iris.enums.ResultType;
import de.baleipzig.iris.logic.neuralnettrainer.BaseTrainer;
import de.baleipzig.iris.logic.neuralnettrainer.result.Result;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

        progress.reset();

        int cycle = 0;
        int trainingCycles = params.getTrainingCycles();
        int trainingRun = 0;
        int cycleLength = params.getTrainingSetSize();

        List<Map.Entry<InputType, OutputType>> shuffledList = new ArrayList<>(trainingData.size());
        shuffledList.addAll(trainingData.entrySet());

        while( cycle < trainingCycles) {

            progress.setCycleProgress(0.);
            progress.setOverallProgress(((double) cycle)/trainingCycles);

            Collections.shuffle(shuffledList);

            for(Map.Entry<InputType, OutputType> trainingDatum : shuffledList) {

                if( interrupted ) {

                    interrupted = false;
                    return new Result(ResultType.FAILURE);
                }

                trainingRun++;

                progress.setCycleProgress((double) trainingRun /cycleLength);

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