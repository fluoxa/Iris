package de.baleipzig.iris.logic.converter.neuralnet;


import de.baleipzig.iris.model.neuralnet.layer.ILayer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DigitConverterTest {

	@DataProvider(name = "convert_ReturnsStateZeroLayer_WhenNumberOutOfRange")
    public static Object[][] convert_data() {
        return new Object[][]{{-1},{10}};
    }
	
	@Test(dataProvider="convert_ReturnsStateZeroLayer_WhenNumberOutOfRange")
	public void convert_ReturnsStateZeroLayer_WhenNumberOutOfRange(int digit){
	
		IEntityLayerConverter<Integer> digitConverter = new DigitConverter();
		
		ILayer layer = digitConverter.convert(digit);
		
		for(int pos = 0; pos < 9; pos++) {
			Assert.assertEquals(layer.getNode(pos, 0).getState() ,0.);
		}
	}
	
	@DataProvider(name = "convert_ExpectedResult_WhenDigitIsInRange")
    public static Object[][] convert_data2() {
		return new Object[][]{
				{0},{1},{2},{3},{4},
				{5},{6},{7},{8},{9}};
    }
	
	@Test(dataProvider="convert_ExpectedResult_WhenDigitIsInRange")
	public void convert_ExpectedResult_WhenDigitIsInRange(int digit){
	
		IEntityLayerConverter<Integer> digitConverter = new DigitConverter();
		
		ILayer layer = digitConverter.convert(digit);
		
		for(int pos = 0; pos < 9; pos++) {
			
			if(pos != digit) {
				Assert.assertEquals(layer.getNode(pos, 0).getState() ,0.);
			}
			else {
				Assert.assertEquals(layer.getNode(pos, 0).getState() ,1.);
			}
		}
	}
}
