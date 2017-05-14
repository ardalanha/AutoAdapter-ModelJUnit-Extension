package hh.mbt.extension.model.CRUDEFSMMulti;

import hh.mbt.extension.DataStorage;
import hh.mbt.extension.Generator;
import io.codearte.jfairy.Fairy;

public class CRUDGeneratorsMulti {	
	
	private DataStorage storage = new DataStorage();
	private Fairy fakeData = Fairy.create();
	
	//CRUD Model Generators
	@Generator(label = "RandModelEntry", type = Integer.class)
	public Integer RandModelEntryGen(){
		int out = fakeData.baseProducer().randomInt(CRUDEFSMMulti.getNumberOfEntry()-1);
		
		return out;
			
	}
	
	@Generator(label = "RandModelEntryDep", type = Integer.class)
	public Integer RandModelEntryDepGen(){
		int out = (Integer) storage.topStorage("RandModelEntry");
		return out;
	}
	
	@Generator(label = "EntryFromModel/StorageRand", type = Integer.class)
	public Integer EntryBasedOnActionGen(){
		int out;
		try {
			out = (Integer) storage.topStorage("RandModelEntry");
		} catch (ArrayIndexOutOfBoundsException e) {
			out = CRUDEFSMMulti.getEntryFromModel();
		}
		catch (NullPointerException e) {
			out = CRUDEFSMMulti.getEntryFromModel();
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
