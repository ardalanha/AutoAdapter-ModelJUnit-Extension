package hh.mbt.extension.model.CRUDEFSMMulti.AbstractOperations;

import java.util.ArrayList;

import hh.mbt.extension.AbstractOperation;

//Abstract ops with concrete configuration that can target both flight booking and grade manager SUT at the same time

public class MultipleSUTOps {
	
private AbstractOperation absOp = new AbstractOperation();
	
	public ArrayList<Object> Init(){
		absOp.newOp();
		absOp.addtargetName("init");
		absOp.addtargetName("setCourse");
		absOp.addParameter(0, String.class, false, true, "CourseName");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Create(){
		absOp.newOp();
		absOp.addtargetName("add");
		absOp.addtargetName("BookFlight");
		absOp.addParameter(0, int.class, false, false, "Not Used");/*Generated Data Not Used-Only for converter invocation*/
		absOp.addParameter(0, String.class, true, true, "Name");
		absOp.addParameter(0, int.class, false, true, "Value");
		absOp.specifyGenerator(0, "Value");
		absOp.storeConcreteOutput("PNR");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Update(){
		absOp.newOp();
		absOp.addtargetName("UpdateBookedFlight");
		absOp.addtargetName("replace");
		absOp.addParameter(0, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0, "RandModelEntry");
		absOp.addParameter(0, String.class, true, true, "Name");
		absOp.addParameter(0, int.class, false, true, "Value");		
		absOp.specifyGenerator(0, "Value");
		absOp.addParameter(1, int.class, false, false, "RandModelEntryDep");
		absOp.specifyGenerator(1, "RandModelEntryDep");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Delete(){
		absOp.newOp();
		absOp.addtargetName("DeleteBookedFlight");
		absOp.addtargetName("remove");
		absOp.addParameter(0, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0, "RandModelEntry");
		absOp.addParameter(1, int.class, false, false, "Not Used");/*Generated Data Not Used-Only for converter invocation*/
		return absOp.createOp();
	}
	
	public ArrayList<Object> Retrieve(){
		absOp.newOp();
		absOp.addtargetName("GetBookedFlight");
		absOp.addtargetName("get");
		absOp.addParameter(0, int.class, false, false, "EntryFromModel/StorageRand");
		absOp.specifyGenerator(0, "EntryFromModel/StorageRand");
		absOp.setReturnType(Object[].class);
		return absOp.createOp();
	}


}
