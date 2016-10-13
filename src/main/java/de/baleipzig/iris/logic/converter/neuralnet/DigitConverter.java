package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.utils.LayerUtils;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public class DigitConverter implements IEntityLayerConverter<Integer> {
	
	public ILayer convert(Integer obj) {
	
		ILayer layer = LayerUtils.createLayer(new Dimension(10,1), false);
		
		if(obj < 0 || obj > 9) {
			return layer;
		}
		
		for(int pos = 0; pos < 10; pos++){
			layer.getNode(pos,0).setState(pos == obj ? 1 : 0);
		}
		
		return layer;
	}
}
