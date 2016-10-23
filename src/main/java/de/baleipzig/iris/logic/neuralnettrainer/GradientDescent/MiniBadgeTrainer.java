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

    private GradientDescentConfig config = null;
    private IEntityLayerAssembler<InputType> inputConverter;
    private IEntityLayerAssembler<OutputType> outputConverter;
    private IGradientDescentNeuralNetTrainer neuralNetTrainingWorker = null;
    private INeuralNetWorker neuralNetWorker = null;
    private MiniBadgeNodeTrainer nodeTrainer = null;

    //endregion

    //region -- constructor --

    public MiniBadgeTrainer(IEntityLayerAssembler<InputType> inputConverter,
                            IEntityLayerAssembler<OutputType> outputConverter,
                            GradientDescentConfig config,
                            IGradientDescentNeuralNetTrainer trainingWorker,
                            INeuralNetWorker worker,
                            IGradientDescentNodeTrainer nodeTrainer) {

        this.inputConverter = inputConverter;
        this.outputConverter = outputConverter;
        this.config = config;
        this.neuralNetTrainingWorker = trainingWorker;
        this.neuralNetWorker = worker;

        if(nodeTrainer instanceof MiniBadgeNodeTrainer){
            this.nodeTrainer  = (MiniBadgeNodeTrainer) nodeTrainer;
        }
        else{
            throw new RuntimeException("MiniBadgeTrainer: IGradientDescentNodeTrainer has to be of Type MiniBadgeNodeTrainer");
        }

    }

    //endregion

    //region -- methods --

    public void setNeuralNet(INeuralNet neuralNet) {

        this.neuralNet = neuralNet;
        this.nodeTrainer.updateBiasWeightCache(neuralNet);
    }

    public void train(Map<InputType, OutputType> trainingData) {

        config.setTrainingSetSize(trainingData.size());

        if(config != null && !config.isValid() && neuralNet == null) {
            throw new RuntimeException("neuralNetTRainer.train(): invalid TrainerConfig");
        }

        for(int cycle = 0; cycle < config.getTrainingCycles(); cycle++){

            System.out.print(cycle + " ");

            trainingData.forEach( (inputData, expectedResult) -> {

                inputConverter.copy(inputData, neuralNet.getNeuralNetCore().getInputLayer());
                neuralNetWorker.propagateForward(neuralNet);

                ILayer expectedResultLayer = outputConverter.convert(expectedResult, null);
                neuralNetTrainingWorker.propagateBackward(neuralNet, expectedResultLayer);
            });
        }
    }

    //endregion
}