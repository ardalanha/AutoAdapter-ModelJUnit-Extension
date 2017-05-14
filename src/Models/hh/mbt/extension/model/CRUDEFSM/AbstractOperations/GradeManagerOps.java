package hh.mbt.extension.model.CRUDEFSM.AbstractOperations;

import java.util.ArrayList;

import hh.mbt.extension.AbstractOperation;


//CRUD abstract operations with Grade Manager concrete configuration

public class GradeManagerOps {
	
	private AbstractOperation absOp = new AbstractOperation();

	public ArrayList<Object> Init(){
		absOp.newOp();
		absOp.addtargetName("setCourse");
		absOp.addParameter(0, String.class, false, true, "CourseName");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Create(){
		absOp.newOp();
		absOp.addtargetName("add");
		absOp.addParameter(0, String.class, true, true, "Name");
		absOp.addParameter(0, int.class, false, true, "Value");
		absOp.specifyGenerator(0, "Value");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Update(){
		absOp.newOp();
		absOp.addtargetName("replace");
		absOp.addParameter(1, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(1, "RandModelEntry");
		absOp.addParameter(0, String.class, true, true, "Name");
		absOp.addParameter(0, int.class, false, true, "Value");		
		absOp.specifyGenerator(0, "Value");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Delete(){
		absOp.newOp();
		absOp.addtargetName("remove");
		absOp.addParameter(0, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0, "RandModelEntry");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Retrieve(){
		absOp.newOp();
		absOp.addtargetName("get");
		absOp.addParameter(0, int.class, false, false, "EntryFromModel/StorageRand");
		absOp.specifyGenerator(0, "EntryFromModel/StorageRand");
		absOp.setReturnType(Object[].class);
		return absOp.createOp();
	}

}
