package de.baleipzig.iris.common.utils;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.node.INode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class LayerUtilsTest {

    private ILayer layer1 = mock(ILayer.class);
    private ILayer layer2 = mock(ILayer.class);
    private INode nodeSource1 = mock(INode.class);
    private INode nodeSource2 = mock(INode.class);
    private INode nodeCopy1 = mock(INode.class);
    private INode nodeCopy2 = mock(INode.class);


    @DataProvider(name = "createLayer_dimensionTest")
    public static Object[][] createLayer_data1() {

        return new Object[][]{
                {new Dimension(0,0), new Dimension(0,0)},
                {new Dimension(1,0), new Dimension(0,0)},
                {new Dimension(0,1), new Dimension(0,0)},
                {new Dimension(2,3), new Dimension(2,3)},
                {new Dimension(4,2), new Dimension(4,2)},
        };
    }

    @Test(dataProvider = "createLayer_dimensionTest")
    public void createLayerWithOptionalRandomBias_LayerHasCorrectDimension_WhenCalled(Dimension inputDim, Dimension expectedDim){

        ILayer layer = LayerUtils.createLayerWithOptionalRandomBias(inputDim, null, false);

        Assert.assertEquals(layer.getDim().getX(), expectedDim.getX());
        Assert.assertEquals(layer.getDim().getY(), expectedDim.getY());
    }

    @DataProvider(name = "createLayer_randomOption")
    public static Object[][] createLayer_data2() {

        return new Object[][]{
                {true, true},
                {false, false}
        };
    }

    @Test(dataProvider = "createLayer_randomOption")
    public void createLayerWithOptionalRandomBias_ReturnsExpectedResult_WhenCalledWithRandomOption(boolean randomize, boolean expectedResult) {

        ILayer result = LayerUtils.createLayerWithOptionalRandomBias(new Dimension(3,2), null, randomize);

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 2; y++){
                INode node = result.getNode(x,y);
                Assert.assertEquals(Math.abs(node.getBias()) > 1.e-10, expectedResult);
            }
        }
    }

    @DataProvider(name = "copyActivationsFromTo_dimensionTest")
    public static Object[][] createLayer_data3() {

        return new Object[][]{
                {new Dimension(1,0), new Dimension(0,0)},
                {new Dimension(0,1), new Dimension(0,0)},
                {new Dimension(2,3), new Dimension(3,2)},
                {new Dimension(4,2), new Dimension(1,3)},
        };
    }

    @Test(dataProvider = "copyActivationsFromTo_dimensionTest", expectedExceptions = RuntimeException.class)
    public void copyActivationsFromTo_ThrowsRuntTimeException(Dimension dimSource, Dimension dimCopy) {

        when(layer1.getDim()).thenReturn(dimSource);
        when(layer2.getDim()).thenReturn(dimCopy);

        LayerUtils.copyActivationFromTo(layer1, layer2);

        verify(layer1).getDim();
        verify(layer2).getDim();
    }

    @Test
    public void copyActivationFromTo_ReturnsTrue_WhenActivationCopied() {

        when(layer1.getDim()).thenReturn(new Dimension(2,1));
        when(layer2.getDim()).thenReturn(new Dimension(2,1));
        when(layer1.getNode(0,0)).thenReturn(nodeSource1);
        when(layer1.getNode(1,0)).thenReturn(nodeSource2);
        when(layer2.getNode(0,0)).thenReturn(nodeCopy1);
        when(layer2.getNode(1,0)).thenReturn(nodeCopy2);
        when(nodeSource1.getActivation()).thenReturn(1.);
        when(nodeSource2.getActivation()).thenReturn(2.);

        LayerUtils.copyActivationFromTo(layer1, layer2);

        verify(nodeCopy1).setActivation(1.);
        verify(nodeCopy2).setActivation(2.);
    }
}