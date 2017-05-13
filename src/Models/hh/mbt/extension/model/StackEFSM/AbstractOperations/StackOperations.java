package hh.mbt.extension.model.StackEFSM.AbstractOperations;

import java.util.ArrayList;

import hh.mbt.extension.AbstractOperation;


public class StackOperations {
	
	private AbstractOperation absOp = new AbstractOperation();
		
	public ArrayList<Object> Push(){
		absOp.newOp();
		absOp.addtargetName("push");
		absOp.addParameter(0, int.class, false, true, "PushData");
		return absOp.createOp();
	}
	
	public ArrayList<Object> Pop(){
		absOp.newOp();
		absOp.addtargetName("pop");
		absOp.setReturnType(int.class);
		return absOp.createOp();
	}
	
	public ArrayList<Object> isEmpty(){
		absOp.newOp();
		absOp.addtargetName("empty");
		absOp.setReturnType(boolean.class);
		return absOp.createOp();
	}
	
	public ArrayList<Object> Top(){
		absOp.newOp();
		absOp.addtargetName("top");
		absOp.addtargetName("peek");
		absOp.setReturnType(int.class);
		return absOp.createOp();
	}

}
