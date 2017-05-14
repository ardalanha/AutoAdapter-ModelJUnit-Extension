package hh.mbt.extension.model.CRUDEFSM.AbstractOperations;

import java.util.ArrayList;

import hh.mbt.extension.AbstractOperation;

//CRUD abstract operations with flight booking web service client concrete configuration
//Link for SUT: https://github.com/raminarmanfar/Online-Ticket-booking-Distributed-program-using-RESTFul-Tomcat-JAVA-

public class FlightBookingOps {	

	private AbstractOperation absOp = new AbstractOperation();
	
	public ArrayList<Object> Init(){
		absOp.newOp();
		absOp.addtargetName("init");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Create(){
		absOp.newOp();
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
		absOp.addParameter(0, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0, "RandModelEntry");
		absOp.addParameter(0, String.class, true, true, "Name");
		absOp.addParameter(0, int.class, false, true, "Value");		
		absOp.specifyGenerator(0, "Value");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Delete(){
		absOp.newOp();
		absOp.addtargetName("DeleteBookedFlight");
		absOp.addParameter(0, int.class, true, true, "RandModelEntry");
		absOp.specifyGenerator(0, "RandModelEntry");
		absOp.addParameter(1, int.class, false, false, "Not Used");/*Generated Data Not Used-Only for converter invocation*/
		return absOp.createOp();
	}
	
	public ArrayList<Object> Retrieve(){
		absOp.newOp();
		absOp.addtargetName("GetBookedFlight");
		absOp.addParameter(0, int.class, false, false, "EntryFromModel/StorageRand");
		absOp.specifyGenerator(0, "EntryFromModel/StorageRand");
		absOp.setReturnType(Object[].class);
		return absOp.createOp();
	}


}
