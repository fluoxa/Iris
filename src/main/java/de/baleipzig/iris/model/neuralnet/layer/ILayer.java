package de.baleipzig.iris.model.neuralnet.layer;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.function.Consumer;

public interface ILayer {

    Dimension dim();

    void clear();
    void resize(Dimension dim);
    void addNode(INode nodeCandidate);
    void removeNode(INode node);

    INode getNode(int x, int y);

    void applyToLayerNodes(Consumer<INode> func);
}
