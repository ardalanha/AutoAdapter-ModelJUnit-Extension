package hh.mbt.extension;

import io.codearte.jfairy.Fairy;

public class DataGenerator {
	
	//Use java object types instead of primitive types to declare type
	private Fairy fakeData = Fairy.create();
	
	@Generator(label = "default", type = Integer.class)
	public Integer intGen(){
		int min = -10000;
		int max = 10000;
		int testData = fakeData.baseProducer().randomBetween(min, max);
		return testData;
	}
	
	@Generator(label = "default", type = Double.class)
	public Double doubleGen(){
		double min = -10000;
		double max = 10000;
		double testData = fakeData.baseProducer().randomBetween(min, max);
		return testData;
	}
	
	@Generator(label = "default", type = Boolean.class)
	public Boolean booleanGen(){
		boolean testData = fakeData.baseProducer().trueOrFalse();
		return testData;
	}
	
	@Generator(label = "default", type = Character.class)
	public Character charGen(){
		char testData = fakeData.baseProducer().randomBetween('a', 'z');
		return testData;
	}
	
	@Generator(label = "default", type = String.class)
	public String stringGen(){
		String testData = fakeData.baseProducer().letterify("??????");
		return testData;
	}
	
	/*
	int[] testData = { 2 , 4 , 6 , 8 };
	int count = 0;
	@Generator(label = "custom", type = Integer.class)
	public Integer intGenCustom(){
		int retval = testData[count];
		count++;
		if(count == testData.length)
			count=0;
		return retval;
	}
	*/
	


}
