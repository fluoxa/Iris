package de.baleipzig.iris.logic.converter.neuralnet;



public class DigitConverterTest {
	
	public ILayer convert(Integer obj) {
	
		ILayer layer = LayerUtils.createFilledLayer(new Dimension(10,1), false);
		
		if(obj < 0 || obj > 9) {
			return layer;
		}
		
		for(int pos = 0; pos < 10; pos++){
			layer.getNode(i,0).setState(pos == obj ? 1 : 0);
		}
		
		return layer;
	}
	
	@DataProvider(name = "convert_ReturnsStateZeroLayer_WhenNumberOutOfRange")
    public static Object[][] convert_data() {
        return new Object[][]{{-1},{10}};
    }
	
	@Test(dataprovider="convert_ReturnsStateZeroLayer_WhenNumberOutOfRange")
	public void convert_ReturnsStateZeroLayer_WhenNumberOutOfRange(int digit){
	
		IEntityLayerConverter digitConverter = new DigitConverter();
		
		ILayer layer = digitConverter.convert(digit);
		
		for(int pos = 0; pos < 9; pos++) {
			Assert.assertEquals(layer.getNode(pos, 0) ,0.);
		}
	}
	
	@DataProvider(name = "convert_ExpectedResult_WhenDigitIsInRange")
    public static Object[][] convert_data() {
        return new Object[][]{{0},{1},{2},{3},{4},{5},{6},{7},{8},{9}};
    }
	
	@Test(dataprovider="convert_ExpectedResult_WhenDigitIsInRange")
	public void convert_ExpectedResult_WhenDigitIsInRange(int digit){
	
		IEntityLayerConverter digitConverter = new DigitConverter();
		
		ILayer layer = digitConverter.convert(digit);
		
		for(int pos = 0; pos < 9; pos++) {
			
			if(pos != digit) {
				Assert.assertEquals(layer.getNode(pos, 0) ,0.);
			}
			else {
				Assert.assertEquals(layer.getNode(pos, 0) ,1.);
			}
		}
	}
}
