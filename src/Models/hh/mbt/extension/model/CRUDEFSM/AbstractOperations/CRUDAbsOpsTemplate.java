package hh.mbt.extension.model.CRUDEFSM.AbstractOperations;

import java.util.ArrayList;

import hh.mbt.extension.AbstractOperation;

//CRUD Abstract Operations Template


public class CRUDAbsOpsTemplate {
	
	private AbstractOperation absOp = new AbstractOperation();
	
	public ArrayList<Object> Create(){
		absOp.newOp();
		absOp.addtargetName(null/*DependsOnSUT*/);
		absOp.addParameter(0/*DependsOnSUT*/, String.class, true, true, "Name");
		absOp.addParameter(1/*DependsOnSUT*/, int.class, false, true, "Value");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Update(){
		absOp.newOp();
		absOp.addtargetName(null/*DependsOnSUT*/);
		absOp.addParameter(0/*DependsOnSUT*/, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0/*DependsOnSUT*/, "RandModelEntry");
		absOp.addParameter(1/*DependsOnSUT*/, String.class, true, true, "Name");
		absOp.addParameter(2/*DependsOnSUT*/, int.class, false, true, "Value");		
		return absOp.createOp();
	}
	
	public ArrayList<Object> Delete(){
		absOp.newOp();
		absOp.addtargetName(null/*DependsOnSUT*/);
		absOp.addParameter(0/*DependsOnSUT*/, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0/*DependsOnSUT*/, "RandModelEntry");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Retrieve(){
		absOp.newOp();
		absOp.addtargetName(null/*DependsOnSUT*/);
		absOp.addParameter(0/*DependsOnSUT*/, int.class, false, false, "EntryFromModel/StorageRand");
		absOp.specifyGenerator(0/*DependsOnSUT*/, "EntryFromModel/StorageRand");
		absOp.setReturnType(Object[].class);
		return absOp.createOp();
	}

}
