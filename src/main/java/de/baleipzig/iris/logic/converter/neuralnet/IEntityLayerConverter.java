package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public interface IEntityLayerConverter<T> {
	
	ILayer convert(T t);
}
