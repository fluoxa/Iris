package de.baleipzig.iris.model.neuralnet.layer;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ILayer {

    Dimension getDim();

    void resize(Dimension dim);
    void addNode(INode nodeCandidate);

    INode getNode(int x, int y);

    void applyToLayerNodes(Consumer<INode> func);
    void applyToLayerNodes(BiConsumer<INode, Object[]> func, Object[] params);
}
