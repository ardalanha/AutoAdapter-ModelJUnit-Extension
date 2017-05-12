package hh.mbt.extension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AutoAdapter {	
	
	private Object sut;
	private DataStorage storage = new DataStorage();
	private static DataConversion convert = new DataConversion();
	private static DataGenerator generate = new DataGenerator();
	static protected File log;
	static private boolean logFlag;
	static private boolean onceAtFirst=true;
	static private long testCaseNumber=0;
	private static Map<String,ArrayList<Object>> freshMap=new HashMap<String,ArrayList<Object>>();
	private static PrintWriter out;
	private boolean onceAtEach=true;

	
	public AutoAdapter(Object sut, boolean log){
		this.sut=sut;
		storage.resetStoreStorage();
		logFlag=log;
		if(logFlag&&onceAtFirst){
			onceAtFirst = false;
			this.logInit();
		}
	}
	
	private void logInit(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		log = new File("log-"+dateFormat.format(date)+".txt");
		if(log.exists()==false){
			try {
				log.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected static void logPrintLine(String in){
		if(logFlag){
			try {
				out = new PrintWriter(new FileWriter(log, true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    out.println(in);
		    out.close();
		}
		//System.out.println(in);
	}

	
	@SuppressWarnings("unchecked")
	public Object adapt(ArrayList<Object> absOp) throws Throwable{
		
		if(onceAtEach){
			onceAtEach=false;
			logPrintLine("\r\n\r\n\r\nTescase number:"+testCaseNumber);
			testCaseNumber++;
		}
		logPrintLine("\r");
		
		//Find method in SUT
		Method m;
		ArrayList<String> namesTemp = (ArrayList<String>) absOp.get(0);
		String[] names=namesTemp.toArray(new String[namesTemp.size()]);
		m = this.getMethod(sut.getClass(), names);
		
		//Generate data for abstract parameters/omitted SUT parameters
		//Concretize abstract data
		//Add all data in sequence in argument array
		Object[] methodArgs=null;
		if(m.getParameterTypes().length!=0){
			ArrayList<Object> methodArgsArr= new ArrayList<Object>();
			Map<Integer,ArrayList<Object>> Parameters = new HashMap<Integer,ArrayList<Object>>();
			if(absOp.get(1)!=null){
				Parameters = (HashMap<Integer, ArrayList<Object>>) absOp.get(1);
			}
			for(int i=0; i<m.getParameterTypes().length; i++){
				Class<?> type;
				Class<?> targetType;
				ArrayList<Object> args = new ArrayList<Object>();
				if(Parameters.containsKey(i)){
					ArrayList<Object> Param= new ArrayList<Object>();
					Param=Parameters.get(i);
					if(Param.size()==6){
						type=primitive2Object((Class<?>) Param.get(0));
						targetType=primitive2Object(m.getParameterTypes()[i]);					
						args.add(Param.get(1));
						args.add(Param.get(2));
						args.add(Param.get(3));
						args.add(Param.get(4));
						logPrintLine("trying to generate abstract data. /label: "+Param.get(3)+"/target order: "+i+"/type: "+type.getName()+"/generator: "+Param.get(4));
						Object genTestData=this.generateData(type, args);
						if(!(type==targetType && Param.get(5).equals("default"))){
							logPrintLine("trying to concretize/convert generated data. /target type: "+targetType.getName()+"/converter: "+Param.get(5));
							methodArgsArr.add(this.convert(type, targetType, genTestData, (String) Param.get(5)));
						}
						else{
							logPrintLine("same abstract and SUT data types, no conversion is specified. /type: "+targetType.getName());
							methodArgsArr.add(genTestData);
						}
					}
					else{
						int size = Param.size()/6;
						Object[] genInputs = new Object[size];
						for(int j=0 ; j<size ; j++){
							type=primitive2Object((Class<?>) Param.get(0+(j*6)));
							args.add(Param.get(1+(j*6)));
							args.add(Param.get(2+(j*6)));
							args.add(Param.get(3+(j*6)));
							args.add(Param.get(4+(j*6)));
							logPrintLine("trying to generate abstract data. /label: "+Param.get(3+(j*6))+"/target order: "+i+"/type: "+type.getName()+"/generator: "+Param.get(4+(j*6)));
							genInputs[j] = this.generateData(type, args);
							args.clear();
						}
						targetType=primitive2Object(m.getParameterTypes()[i]);
						logPrintLine("trying to concretize multiple generated data. /target type: "+targetType.getName()+"/converter: "+Param.get(5));
						methodArgsArr.add(this.convertMulti(targetType, genInputs, (String) Param.get(5)));
					}
				}
				else{					
					targetType=primitive2Object(m.getParameterTypes()[i]);
					args.add(false);
					args.add(false);
					args.add("omitted SUT parameter");
					args.add("default");
					logPrintLine("trying to generate input data. /label: omitted SUT parameter/target order: "+i+"/target type: "+targetType.getName()+"/generator: default");
					methodArgsArr.add(this.generateData(targetType, args));
				}
			}
			methodArgs=methodArgsArr.toArray();
		}
		
		//Invoke method
		Object output=null;
		m.setAccessible(true);
		try {
			output=m.invoke(sut, methodArgs);
		} catch (IllegalArgumentException e) {
			AutoAdapter.logPrintLine("Adapter Error: "+e.toString());
		} catch (IllegalAccessException e) {
			AutoAdapter.logPrintLine("Adapter Error: "+e.toString());
		} catch (InvocationTargetException e) {
			logPrintLine("SUT method "+m.getName()+" threw exception: "+e.getTargetException());
			throw e.getTargetException();
		}
		logPrintLine("SUT method "+m.getName()+" is executed");
		
		//store SUT method output
		if(!absOp.get(4).equals("null")){
			storage.addToStorage(true, (String)absOp.get(4), output);
		}
		
		//Make returned data abstract if required
		if(absOp.get(2)!=null){
			Class<?> type_O;
			Class<?> targerType_O;
			type_O=primitive2Object(m.getReturnType());
			targerType_O=primitive2Object((Class<?>) absOp.get(2));
			
			if(!(type_O==targerType_O && absOp.get(3).equals("default"))){
				logPrintLine("trying to make output data abstract. /from "+type_O.getName()+" to "+targerType_O.getName()+"/converter: "+(String) absOp.get(3));
				output=this.convert(type_O, targerType_O, output, (String) absOp.get(3));
			}
			else{
				logPrintLine("same SUT and abstract output data types, no conversion is specified. /type: "+type_O.getName());
			}
			logPrintLine("output data from SUT is: "+output);
		}
		
				
		return output;
		
	}

	
	
	private Object generateData(Class<?> type, ArrayList<Object> args){
		Object out=null;
		String genLabel = (String) args.get(3);
		//match and convert
		Method[] methods=generate.getClass().getMethods();
		boolean errorMsgFlag=true;
		for (Method method : methods){
			 if (method.isAnnotationPresent(Generator.class)){
				 Generator genM = method.getAnnotation(Generator.class);
				 if(genM.type()==type){
					 if(genM.label().equals(genLabel)){
						 errorMsgFlag=false;
						 AutoAdapter.logPrintLine(genLabel+" generator for "+type+" is found");
						 if((Boolean)args.get(0)){
								ArrayList<Object> freshBackupList = new ArrayList<Object>();
								if(freshMap.containsKey((String)args.get(2))){
									freshBackupList=freshMap.get((String)args.get(2));
								}
								int countTry=0;
								while(true){
									try {
										out = method.invoke(generate);
									} catch (IllegalArgumentException e) {
										AutoAdapter.logPrintLine("Generator Error: "+e.toString());
									} catch (IllegalAccessException e) {
										AutoAdapter.logPrintLine("Generator Error: "+e.toString());
									} catch (InvocationTargetException e) {
										AutoAdapter.logPrintLine("Generator Error: "+e.toString());
									}
									if(!freshBackupList.contains(out)){
										freshBackupList.add(out);
										freshMap.put((String)args.get(2), freshBackupList);
										break;
									}
									countTry++;
									if(countTry>20){
										AutoAdapter.logPrintLine("all data are tested for: "+(String)args.get(2)+". 20 repetition to generate fresh data");
										break;
									}
								}
							}
						 else{
							 try {
									out = method.invoke(generate);
								} catch (IllegalArgumentException e) {
									AutoAdapter.logPrintLine("Generator Error: "+e.toString());
								} catch (IllegalAccessException e) {
									AutoAdapter.logPrintLine("Generator Error: "+e.toString());
								} catch (InvocationTargetException e) {
									AutoAdapter.logPrintLine("Generator Error: "+e.getTargetException());
								}
						 }
						 logPrintLine("generated data: "+out+" / label: "+(String)args.get(2));
						 storage.addToStorage((Boolean)args.get(1),(String)args.get(2), out);
					 }
				 }
			 }
		}
		if(errorMsgFlag){
			AutoAdapter.logPrintLine("Error: "+genLabel+" generator for "+type+" is not found");
		}
		
		return out;
	}
	
	
	private Object convert(Class<?> type, Class<?> targetType, Object value, String convLabel) {
		//match and convert
		Method[] methods=convert.getClass().getMethods();
		boolean errorMsgFlag=true;
		for (Method method : methods){
			 if (method.isAnnotationPresent(Converter.class)){
				 Converter convM = method.getAnnotation(Converter.class);
				 if(convM.type()==type){
					 if(convM.targetType()==targetType){
						 if(convM.label().equals(convLabel)){
							 errorMsgFlag=false;
							 AutoAdapter.logPrintLine(convLabel+" converter from "+type.getName()+" to "+targetType.getName()+" is found");
							 try {
								value = method.invoke(convert,value);
							} catch (IllegalArgumentException e) {
								AutoAdapter.logPrintLine("Converter Error: "+e.toString());
							} catch (IllegalAccessException e) {
								AutoAdapter.logPrintLine("Converter Error: "+e.toString());
							} catch (InvocationTargetException e) {
								AutoAdapter.logPrintLine("Converter Error: "+e.getTargetException());
							}
						 }
					 }
				 }
			 }
		}
		if(errorMsgFlag){
			AutoAdapter.logPrintLine("Error: "+convLabel+" converter from "+type.getName()+" to "+targetType.getName()+" is not found");
		}		

		return value;
		
	}
	
	private Object convertMulti(Class<?> targetType, Object[] genInputs, String convLabel) {
		Object value = null;
		Method[] methods=convert.getClass().getMethods();
		boolean errorMsgFlag=true;
		for (Method method : methods){
			if (method.isAnnotationPresent(Converter.class)){
				 Converter convM = method.getAnnotation(Converter.class);
				 if(convM.multiple()){
					 if(convM.targetType()==targetType){
						 if(convM.label().equals(convLabel)){
							 errorMsgFlag=false;
							 AutoAdapter.logPrintLine(convLabel+" many to one converter to "+targetType.getName()+" is found");
							 try {
								value = method.invoke(convert,genInputs);
							} catch (IllegalArgumentException e) {
								AutoAdapter.logPrintLine("Many2One Converter Error: "+e.toString());
							} catch (IllegalAccessException e) {
								AutoAdapter.logPrintLine("Many2One Converter Error: "+e.toString());
							} catch (InvocationTargetException e) {
								AutoAdapter.logPrintLine("Many2One Converter Error: "+e.getTargetException());
							}
						 }						 
					 }					 
				 }
			}
		}
		if(errorMsgFlag){
			AutoAdapter.logPrintLine("Error: "+convLabel+" many2one converter to "+targetType.getName()+" is not found");
		}
		
		return value;
	}
	
	private Class<?> primitive2Object(Class<?> type){
		if(type==int.class){
			type=Integer.class;
		}
		else if(type==boolean.class){
			type=Boolean.class;
		}
		else if(type==double.class){
			type=Double.class;
		}
		else if(type==float.class){
			type=Float.class;
		}
		else if(type==long.class){
			type=Long.class;
		}
		else if(type==char.class){
			type=Character.class;
		}
		else if(type==short.class){
			type=Short.class;
		}
		else if(type==byte.class){
			type=Byte.class;
		}
		
		return type;
		
	}

	private Method getMethod(Class<?> sut, String[] samples){
	    if (sut != null) {
	      for (Method m1 : sut.getDeclaredMethods()) {
	    	  for(String sample : samples){
		    	  if (m1.getName().toLowerCase().contains(sample.toLowerCase())) {
		    		    logPrintLine("method is found as "+m1.getName()+" in SUT");
			            return m1;
			          }
	    	  }
	      }
	      logPrintLine("method "+samples[0]+" is not found in SUT");
	      return null;
	  }
	    else{
	    	logPrintLine("SUT class is null");
			return null;
	    }
	}
}
