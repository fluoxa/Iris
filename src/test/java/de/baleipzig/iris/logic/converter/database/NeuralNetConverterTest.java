package de.baleipzig.iris.logic.converter.database;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.enums.FunctionType;
import de.baleipzig.iris.model.neuralnet.activationfunction.SigmoidFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;
import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetCore;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNetMetaData;
import de.baleipzig.iris.model.neuralnet.node.INode;
import de.baleipzig.iris.persistence.entity.neuralnet.AxonEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.LayerEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NeuralNetEntity;
import de.baleipzig.iris.persistence.entity.neuralnet.NodeEntity;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

import static org.mockito.Mockito.*;

public class NeuralNetConverterTest {

    //region -- member --

    private INode node = mock(INode.class);
    private INode node0 = mock(INode.class);
    private INode node1 = mock(INode.class);
    private INode node2 = mock(INode.class);
    private INode node3 = mock(INode.class);
    private IAxon axon02 = mock(IAxon.class);
    private IAxon axon12 = mock(IAxon.class);
    private IAxon axon23 = mock(IAxon.class);
    private ILayer inputLayer = mock(ILayer.class);
    private ILayer hiddenLayer = mock(ILayer.class);
    private ILayer outputLayer = mock(ILayer.class);
    private INeuralNetCore netCore = mock(INeuralNetCore.class);
    private INeuralNet neuralNet = mock(INeuralNet.class);
    private INeuralNetMetaData neuralNetMetaData = mock(INeuralNetMetaData.class);

    private List<INode> nodeList;
    private Map<INode, Long> nodeIdMapper;

    //endregion

    //region -- setup --

    @BeforeMethod
    public void setupTestNeuralNet(){

        when(node0.getBias()).thenReturn(0.);
        when(node0.getActivationFunctionContainer()).thenReturn(new SigmoidFunctionContainer());
        when(node0.getChildAxons()).thenReturn(Collections.singletonList(axon02));
        when(node1.getBias()).thenReturn(1.);
        when(node1.getChildAxons()).thenReturn(Collections.singletonList(axon12));
        when(node1.getActivationFunctionContainer()).thenReturn(new SigmoidFunctionContainer());
        when(node2.getBias()).thenReturn(2.);
        when(node2.getParentAxons()).thenReturn(Arrays.asList(axon02, axon12));
        when(node2.getChildAxons()).thenReturn(Collections.singletonList(axon23));
        when(node2.getActivationFunctionContainer()).thenReturn(new SigmoidFunctionContainer());
        when(node3.getBias()).thenReturn(3.);
        when(node3.getParentAxons()).thenReturn(Collections.singletonList(axon23));
        when(node3.getActivationFunctionContainer()).thenReturn(new SigmoidFunctionContainer());

        when(axon02.getChildNode()).thenReturn(node2);
        when(axon02.getParentNode()).thenReturn(node0);
        when(axon12.getChildNode()).thenReturn(node2);
        when(axon12.getParentNode()).thenReturn(node1);
        when(axon23.getChildNode()).thenReturn(node3);
        when(axon23.getParentNode()).thenReturn(node2);

        when(inputLayer.getDim()).thenReturn(new Dimension(2,1));
        when(inputLayer.getNode(0,0)).thenReturn(node0);
        when(inputLayer.getNode(1,0)).thenReturn(node1);
        when(hiddenLayer.getDim()).thenReturn(new Dimension(1,1));
        when(hiddenLayer.getNode(0,0)).thenReturn(node2);
        when(outputLayer.getDim()).thenReturn(new Dimension(1,1));
        when(outputLayer.getNode(0,0)).thenReturn(node3);

        when(netCore.getHiddenLayers()).thenReturn(Collections.singletonList(hiddenLayer));
        when(netCore.getOutputLayer()).thenReturn(outputLayer);
        when(netCore.getInputLayer()).thenReturn(inputLayer);

        nodeList = Arrays.asList(node0, node1, node2, node3);
        nodeIdMapper = new HashMap<>(4);
        nodeIdMapper.put(node0, 0L);
        nodeIdMapper.put(node1, 1L);
        nodeIdMapper.put(node2, 2L);
        nodeIdMapper.put(node3, 3L);

        when(neuralNet.getNeuralNetCore()).thenReturn(netCore);
        when(neuralNet.getNeuralNetMetaData()).thenReturn(neuralNetMetaData);
        when(neuralNetMetaData.getName()).thenReturn("jan");
        when(neuralNetMetaData.getDescription()).thenReturn("jan ist cool");
    }

    @AfterMethod
    public void resetMocks(){

        reset(node0);
        reset(node1);
        reset(node2);
        reset(node3);
        reset(axon02);
        reset(axon12);
        reset(axon23);
        reset(inputLayer);
        reset(hiddenLayer);
        reset(outputLayer);
        reset(netCore);
        reset(neuralNet);
        reset(neuralNetMetaData);
    }

    //endregion

    //region -- methods --

    @Test
    public void getAxonEntities_CreatesCorrectAxons(){

        Map<String, AxonEntity> result = NeuralNetConverter.getAxonEntities(nodeList, nodeIdMapper);

        Assert.assertTrue(result.containsKey("0-2"), "Contains Key");
        Assert.assertTrue(result.containsKey("1-2"), "Contains Key");
        Assert.assertTrue(result.containsKey("2-3"), "Contains Key");

        Assert.assertEquals(result.get("0-2").getChildNodeId(), 2 );
        Assert.assertEquals(result.get("0-2").getParentNodeId(), 0);
        Assert.assertEquals(result.get("1-2").getChildNodeId(), 2 );
        Assert.assertEquals(result.get("1-2").getParentNodeId(), 1);
        Assert.assertEquals(result.get("2-3").getChildNodeId(), 3 );
        Assert.assertEquals(result.get("2-3").getParentNodeId(), 2);
    }

    @Test
    public void toNodeEntity_ReturnsCorrectNodeEntity(){

        NodeEntity result = NeuralNetConverter.toNodeEntity(node2, nodeIdMapper);

        Assert.assertEquals(result.getNodeId(),2);
        Assert.assertEquals(result.getBias(), 2.);
        Assert.assertEquals(result.getActivationFunctionType(), FunctionType.SIGMOID.toString());

        verify(node2, times(1)).getBias();
    }

    @Test
    public void toNodeEntity_ReturnsNodeEntityWhenActivationFunctionContainerNull(){

        when(node.getActivationFunctionContainer()).thenReturn(null);
        nodeIdMapper.put(node, 12L);

        NodeEntity result = NeuralNetConverter.toNodeEntity(node, nodeIdMapper);

        Assert.assertEquals(result.getActivationFunctionType(), FunctionType.NONE.toString());

        verify(node).getActivationFunctionContainer();
    }

    @Test
    public void toLayerEntity_ReturnsCorrectLayerEntity(){

        LayerEntity result = NeuralNetConverter.toLayerEntity(inputLayer, nodeIdMapper);

        Assert.assertEquals(result.getDimX(), 2);
        Assert.assertEquals(result.getDimY(), 1);
        Assert.assertEquals(result.getNodes().size(), 2);

        verify(inputLayer,times(4)).getDim();
    }

    @DataProvider(name = "toLayerEntity_NodesAreOrderedCorrectly")
    public static Object[][] toLayerEntity_data() {
        return new Object[][]{
                {0,0},
                {1,1}
        };
    }

    @Test(dataProvider = "toLayerEntity_NodesAreOrderedCorrectly")
    public void toLayerEntity_NodesAreOrderedCorrectly(int listIndex, int expectedNodeId){

        LayerEntity result = NeuralNetConverter.toLayerEntity(inputLayer, nodeIdMapper);

        Assert.assertEquals(result.getNodes().get(listIndex).intValue(), expectedNodeId);
    }

    @Test
    public void toNeuralNetEntity_YieldsCorrectNeuralNetEntity(){

        UUID id = UUID.randomUUID();
        when(neuralNetMetaData.getId()).thenReturn(id);

        NeuralNetEntity result = NeuralNetConverter.toNeuralNetEntity(neuralNet);

        Assert.assertEquals(result.getNeuralNetId(),id.toString());
        Assert.assertEquals(result.getDescription(), "jan ist cool");
        Assert.assertEquals(result.getName(), "jan");
        Assert.assertEquals(result.getNeuralNetCoreEntity().getNodes().size(), 4);
        Assert.assertEquals(result.getNeuralNetCoreEntity().getAxons().size(), 3);
        Assert.assertEquals(result.getNeuralNetCoreEntity().getLayers().size(), 3);
    }

    //endregion
}