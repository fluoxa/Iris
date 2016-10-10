package de.baleipzig.irisTest;

import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.node.Node;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NodeTest {

    @Mock
    IAxon axon;

    @Test
    public void addChildNode_ChildAxonsHasCorrectSize_AfterAddingAxon(){

        Node node = new Node();

        node.addChildAxon(axon);

        Assert.assertEquals(1, node.getChildAxons().size());
    }

    @Test
    public void addParentNode_ParentAxonsHasCorrectSize_AfterAddingAxon(){

        Node node = new Node();

        node.addParentAxon(axon);

        Assert.assertEquals(1, node.getParentAxons().size());
    }
}