package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public class DigitConverter implements IEntityLayerConverter<Integer> {

    private final int MAX_DIGIT = 9;
    private final int MIN_DIGIT = 0;

	public ILayer convert(Integer digit) {
	
		ILayer layer = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(10,1), null,false);
		
		if(digit < MIN_DIGIT || digit > MAX_DIGIT) {
			throw new RuntimeException("DigitConverter: digit out of range");
		}
		
		for(int pos = MIN_DIGIT; pos <= MAX_DIGIT; pos++){
			layer.getNode(pos,0).setState(pos == digit ? 1 : 0);
		}
		
		return layer;
	}
}