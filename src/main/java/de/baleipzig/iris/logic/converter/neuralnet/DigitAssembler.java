package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.Pair;
import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.model.neuralnet.activationfunction.IFunctionContainer;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public class DigitAssembler implements IAssembler<Integer> {

	private final static int MAX_DIGIT = 9;
	private final static int MIN_DIGIT = 0;
	private final static double RECOGNITION_THRESHOLD = 0.71;

	public ILayer convert(Integer digit, IFunctionContainer funcContainer) {

		ILayer layer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(10,1), funcContainer, false);

		copy(digit, layer);

		return layer;
	}

	public void copy (Integer digit, ILayer layer) {

		if(digit < MIN_DIGIT || digit > MAX_DIGIT) {
			throw new RuntimeException("DigitConverter: digit out of range");
		}

		for(int pos = MIN_DIGIT; pos <= MAX_DIGIT; pos++){
			layer.getNode(pos,0).setActivation(pos == digit ? 1 : 0);
		}
	}

	public Integer convert(ILayer outputLayer) {

		Dimension layerDimension = outputLayer.getDim();

		if(!layerDimension.equals(new Dimension(MAX_DIGIT-MIN_DIGIT+1,1))) {
			throw new RuntimeException("DigitConverter.convert: layerDimension must be equal to ("+ (MAX_DIGIT-MIN_DIGIT+1) +",1)");
		}

		Pair<Double, Integer> predictedValue = new Pair<>(outputLayer.getNode(0,0).getActivation(),MIN_DIGIT);

		for(int posX = 1; posX < MAX_DIGIT-MIN_DIGIT+1; posX++ ) {

			double activation = outputLayer.getNode(posX, 0).getActivation();

			if(activation > predictedValue.getKey()) {
				predictedValue = new Pair<>(activation, MIN_DIGIT+posX);
			}
		}

		return  predictedValue.getKey() > RECOGNITION_THRESHOLD ? predictedValue.getValue() : -1;
	}
}