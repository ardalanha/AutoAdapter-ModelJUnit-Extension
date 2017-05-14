package hh.mbt.extension;

public class DataConversion {
		
	//Use java object types instead of primitive types to declare type and targetType
	//For same type and targetType in case of different data conversions using default label is not allowed
	//For conversions with multiple input, the sequence of adding abstract operation parameters is important
	
	@Converter(label = "default", type = Integer.class, targetType = Double.class)
	public Double Integer2Double(Integer input){
		
		double output = (double) input.intValue();
		return output;
		
	}
	
	@Converter(label = "default", type = Double.class, targetType = Integer.class)
	public Integer Double2Integer(Double input){
		
		int output = (int) input.doubleValue();
		return output;
		
	}
	
	@Converter(label = "default", type = Integer.class, targetType = String.class)
	public String Integer2String(Integer input){
		
		String output = String.valueOf(input);
		return output;
		
	}
	
	@Converter(label = "default", type = String.class, targetType = Integer.class)
	public Integer String2Integer(String input){
		
		int output = Integer.parseInt(input);
		return output;
		
	}
	

}

