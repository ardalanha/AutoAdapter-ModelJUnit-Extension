package hh.mbt.extension.model.CRUDEFSM;

import hh.mbt.extension.DataStorage;
import hh.mbt.extension.Generator;
import io.codearte.jfairy.Fairy;

public class CRUDGenerators {
	
	private DataStorage storage = new DataStorage();
	private Fairy fakeData = Fairy.create();
	
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
	
	@Generator(label = "Value", type = Integer.class)
	public Integer intGen(){
		int min = 1;
		int max = 20;
		int testData = fakeData.baseProducer().randomBetween(min, max);
		return testData;
	}

}
