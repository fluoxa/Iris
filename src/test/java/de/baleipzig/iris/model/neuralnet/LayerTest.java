package de.baleipzig.iris.model.neuralnet;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.layer.Layer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class LayerTest {

    private INode node = mock(INode.class);
    private INode node2 = mock(INode.class);
    private int counter = 0;

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

    @DataProvider(name = "applyToLayerNodes_DoesNothing_WhenArrayIsEmpty")
    public static Object[][] applyToLayerNodes_data() {
        return new Object[][]{
                {new Dimension(0, 1)},
                {new Dimension(1, 0)},
                {new Dimension(0, 0)},
                {new Dimension(2, 3)}
        };
    }

    @Test(dataProvider = "dim_returnsCorrectDim")
    public void dim_returnsCorrectDim(Dimension dim, int x, int y){

        Layer layer = new Layer();
        layer.resize(dim);

        Dimension result = layer.getDim();

        Assert.assertEquals(result.getX(), x);
        Assert.assertEquals(result.getY(), y);
    }

    @Test
    public void clear_LayerIsResetWithNullEntries_AfterCallingClear() {

        Layer layer = new Layer();
        layer.resize(new Dimension(3,2));
        layer.addNode(node);
        layer.addNode(node);

        layer.clear();

        Assert.assertEquals(layer.getDim().getX(), 3);
        Assert.assertEquals(layer.getDim().getY(), 2);

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

        Assert.assertEquals(layer.getNode(0,0),node);
        Assert.assertEquals(layer.getNode(1,0),node2);
        Assert.assertNull(layer.getNode(1,1));
        Assert.assertNull(layer.getNode(0,1));
    }

    @Test
    public void addNode_LayerDoesNotContainNode2_WhenLayerIsFull(){

        Layer layer = new Layer();
        layer.resize(new Dimension(1,1));
        layer.addNode(node);

        layer.addNode(node2);

        Assert.assertNotEquals(layer.getNode(0,0), node2);
    }

    @Test
    public void removeNode_LayerContainsNode_AfterAddingNode(){

        Layer layer = new Layer();
        layer.resize(new Dimension(2,2));
        layer.addNode(node);
        layer.addNode(node2);

        layer.removeNode(node2);

        Assert.assertEquals(layer.getNode(0,0),node);
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

        Assert.assertEquals(result, node2);
    }

    @Test
    public void applyToLayerNodes_AppliesFunctionToEveryLayerNode(){

        Layer layer = new Layer();
        layer.resize(new Dimension(1,3));
        layer.addNode(node);
        layer.addNode(node2);
        Consumer<INode> func = x -> {x.setActivation(3.); this.counter++;};

        layer.applyToLayerNodes(func);

        Assert.assertEquals(this.counter, 2);
        verify(node, times(1)).setActivation(3.);
        verify(node2, times(1)).setActivation(3.);
    }

    @Test(dataProvider = "applyToLayerNodes_DoesNothing_WhenArrayIsEmpty")
    public void applyToLayerNodes_DoesNothing_WhenArrayIsEmptyOrZeroSized(Dimension dim){

        Layer layer = new Layer();
        layer.resize(dim);
        this.counter = 0;
        Consumer<INode> func = (x) -> {x.setActivation(3.); this.counter++;};

        layer.applyToLayerNodes(func);

        Assert.assertEquals(counter, 0);
    }
}