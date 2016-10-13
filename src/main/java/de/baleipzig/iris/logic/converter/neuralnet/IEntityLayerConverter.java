package de.baleipzig.iris.logic.converter.neuralnet;

public interface IEntityLayerConverter<T> {
	
	ILayer convert(T obj);
}
