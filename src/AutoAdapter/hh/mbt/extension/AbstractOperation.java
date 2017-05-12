package hh.mbt.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbstractOperation {
	private ArrayList<String> targetNames;
	private Map<Integer,ArrayList<Object>> Parameters;
	private Class<?> returnType;
	private String retLabel;
	private ArrayList<Object> absOp;
	private String retConversionMLabel;
	
	
	public void newOp(){
		targetNames = new ArrayList<String>();
		Parameters=new HashMap<Integer,ArrayList<Object>>();
		returnType=null;
		retLabel=null;
		absOp = new ArrayList<Object>();
		retConversionMLabel = "default";
	}
	
	public void addtargetName(String name){
		targetNames.add(name);
	}
	
	
	//any type as input should contain generator
	public void addParameter(int targetOrder, Class<?> type, boolean fresh, boolean store, String label){
		ArrayList<Object> paramData = new ArrayList<Object>();
		if(Parameters.containsKey(targetOrder))
			paramData=Parameters.get(targetOrder);
		paramData.add(type);
		paramData.add(fresh);
		paramData.add(store);
		if(label!=null)
			paramData.add(label);
		else
			paramData.add("null");
		paramData.add("default");
		paramData.add("default");
		Parameters.put(targetOrder, paramData);
		
	}
	

	
	
	public void specifyConversion(int targetOrder, String convLabel){
		ArrayList<Object> paramData = new ArrayList<Object>();
		if(Parameters.containsKey(targetOrder)){
			paramData = Parameters.get(targetOrder);
			paramData.set(5, convLabel);
		}
	}
	
	public void specifyGenerator(int targetOrder, String genLabel){
		ArrayList<Object> paramData = new ArrayList<Object>();
		if(Parameters.containsKey(targetOrder)){
			paramData = Parameters.get(targetOrder);
			int size = paramData.size();
			int index = (size-2);
			paramData.set(index, genLabel);
		}
	}
	
	public void specifyRetConversion(String convLabel){
		retConversionMLabel = convLabel;
	}
	
	public void setReturnType(Class<?> type){
		returnType=type;
		
	}
	
	public void storeConcreteOutput(String Label){
		retLabel=Label;
		
	}
	
	public ArrayList<Object> createOp(){
		//1.Names
		absOp.add(targetNames);
		//2.Parameters
		if(!Parameters.isEmpty()){
			absOp.add(Parameters);
		}
		else{
			absOp.add(null);
		}
		//3.Return Type
		if(returnType!=null){
			absOp.add(returnType);
			absOp.add(retConversionMLabel);
		}
		else{
			absOp.add(null);
			absOp.add("null");
		}
		
		//storage label for retval
		if(retLabel==null){
			absOp.add("null");
		}
		else{
			absOp.add(retLabel);
		}
		
		
		return absOp;
		
	}
	
	
	
	

}
