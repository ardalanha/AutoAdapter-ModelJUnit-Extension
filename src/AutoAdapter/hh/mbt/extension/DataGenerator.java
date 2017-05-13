package hh.mbt.extension;

import hh.mbt.extension.model.CRUDEFSM.CRUDEFSM;
import io.codearte.jfairy.Fairy;

public class DataGenerator {
	
	//Use java object types instead of primitive types to declare type
	private Fairy fakeData = Fairy.create();
	private DataStorage storage = new DataStorage();
	
	@Generator(label = "default", type = Integer.class)
	public Integer intGen(){
		int min = 0;
		int max = 10;
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
	
	//CRUD Model Generators
	@Generator(label = "RandModelEntry", type = Integer.class)
	public Integer RandModelEntryGen(){
		int out = fakeData.baseProducer().randomInt(CRUDEFSM.getNumberOfEntry()-1);
		
		return out;
			
	}
	
	@Generator(label = "EntryFromModel/StorageRand", type = Integer.class)
	public Integer EntryBasedOnActionGen(){
		int out;
		try {
			out = (Integer) storage.topStorage("RandModelEntry");
		} catch (ArrayIndexOutOfBoundsException e) {
			out = CRUDEFSM.getEntryFromModel();
		}
		catch (NullPointerException e) {
			out = CRUDEFSM.getEntryFromModel();
		}
		return out;
			
	}

	



}
