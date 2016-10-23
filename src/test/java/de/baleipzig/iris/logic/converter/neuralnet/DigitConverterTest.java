package de.baleipzig.iris.logic.converter.neuralnet;

import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DigitConverterTest {

	@DataProvider(name = "convert_ReturnsActivationZeroLayer_WhenNumberOutOfRange")
    public static Object[][] convert_data() {
        return new Object[][]{{-1},{10}};
    }
	
	@DataProvider(name = "convert_ExpectedResult_WhenDigitIsInRange")
    public static Object[][] convert_data2() {
		return new Object[][]{
				{0},{1},{2},{3},{4},
				{5},{6},{7},{8},{9}};
    }

	@Test(dataProvider = "convert_ReturnsActivationZeroLayer_WhenNumberOutOfRange", expectedExceptions = RuntimeException.class)
	public void convert_ReturnsActivationZeroLayer_WhenNumberOutOfRange(int digit) throws RuntimeException {

		IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();

		ILayer layer = digitConverter.convert(digit, null);
	}
	
	@Test(dataProvider="convert_ExpectedResult_WhenDigitIsInRange")
	public void convert_ExpectedResult_WhenDigitIsInRange(int digit){
	
		IEntityLayerAssembler<Integer> digitConverter = new DigitAssembler();
		
		ILayer layer = digitConverter.convert(digit, null);
		
		for(int pos = 0; pos < 9; pos++) {
			
			if(pos != digit) {
				Assert.assertEquals(layer.getNode(pos, 0).getActivation() ,0.);
			}
			else {
				Assert.assertEquals(layer.getNode(pos, 0).getActivation() ,1.);
			}
		}
	}
}
