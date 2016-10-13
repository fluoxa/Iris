package de.baleipzig.iris.logic.converter.neuralnet;

public class DigitConverter implements IEntityLayerConverter<Integer> {
	
	public ILayer convert(Integer obj) {
	
		ILayer layer = LayerUtils.createFilledLayer(new Dimension(10,1), false);
		
		if(obj < 0 || obj > 9) {
			return layer;
		}
		
		for(int pos = 0; pos < 10; pos++){
			layer.getNode(i,0).setState(pos == obj ? 1 : 0);
		}
		
		return layer;
	}
}
