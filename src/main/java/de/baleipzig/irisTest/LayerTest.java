package de.baleipzig.irisTest;


import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LayerTest {

    private INode node = mock(INode.class);
    private INode node2 = mock(INode.class);

    @DataProvider(name = "dim_returnsCorrectDim")
    public static Object[][] dimensions() {
        return new Object[][] {
            {new Dimension(1,3), 1, 3},
            {new Dimension(8,1), 8, 1},
            {new Dimension(0,1), 0, 0},
            {new Dimension(1,0), 0, 0},
            {new Dimension(0,0), 0, 0}
        };
    }

    @Test(dataProvider = "dim_returnsCorrectDim")
    public void dim_returnsCorrectDim(Dimension dim, int x, int y){

        Layer layer = new Layer();
        layer.resize(dim);

        Dimension result = layer.dim();

        Assert.assertEquals(x, result.getX());
        Assert.assertEquals(y, result.getY());
    }

    @Test
    public void clear_LayerIsResetWithNullEntries_AfterCallingClear(){

        Layer layer = new Layer();
        layer.resize(new Dimension(3,2));
        layer.addNode(node);
        layer.addNode(node);

        layer.clear();

        Assert.assertEquals(3, layer.dim().getX());
        Assert.assertEquals(2, layer.dim().getY());

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 3; j++)
                Assert.assertNull(layer.getNode(j,i));
    }

    @Test
    public void addNode_LayerContainsNodes_AfterAddingTwoNodes(){

        Layer layer = new Layer();
        layer.resize(new Dimension(2,2));

        layer.addNode(node);
        layer.addNode(node2);

        Assert.assertEquals(node, layer.getNode(0,0));
        Assert.assertEquals(node2, layer.getNode(1,0));
        Assert.assertNull(layer.getNode(1,1));
        Assert.assertNull(layer.getNode(0,1));
    }

    @Test
    public void removeNode_LayerContainsNode_AfterAddingNode(){

        Layer layer = new Layer();
        layer.resize(new Dimension(2,2));
        layer.addNode(node);
        layer.addNode(node2);

        layer.removeNode(node2);

        Assert.assertEquals(node, layer.getNode(0,0));
        Assert.assertNull(layer.getNode(1,0));
        Assert.assertNull(layer.getNode(1,1));
        Assert.assertNull(layer.getNode(0,1));
    }

    @Test
    public void getNode_ReturnsCorrectNode(){

        Layer layer = new Layer();
        layer.resize(new Dimension(2,2));
        layer.addNode(node);
        layer.addNode(node2);

        INode result = layer.getNode(1,0);

        Assert.assertEquals(node2, result);
    }

    @Test
    public void applyToLayerNodes_AppliesFunctionToEveryLayerNode(){

        Layer layer = new Layer();
        layer.resize(new Dimension(1,2));
        layer.addNode(node);
        layer.addNode(node2);
        Consumer<INode> func = x -> x.setState(3.);

        layer.applyToLayerNodes(func);

        verify(node, times(1)).setState(3.);
        verify(node2, times(1)).setState(3.);
    }
}