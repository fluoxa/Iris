package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.model.neuralnet.activationfunction.IFunctionContainer;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;

public interface IEntityLayerAssembler<T> {

	ILayer convert(T t, IFunctionContainer funcContainer);
	void copy(T t, ILayer layer);
}
