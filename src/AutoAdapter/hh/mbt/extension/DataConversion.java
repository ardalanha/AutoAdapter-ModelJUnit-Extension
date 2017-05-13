package hh.mbt.extension;

import hh.mbt.extension.model.CRUDEFSM.CRUDEFSM;

public class DataConversion {
		
	//Use java object types instead of primitive types to declare type and targetType
	//For same type and targetType in case of different data conversions using default label is not allowed
	//For conversions with multiple input, the sequence of adding abstract operation parameters is important
	private DataStorage storage = new DataStorage();
	
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
	
	//CRUD - FlightBooking Converters
	@Converter(targetType = restClientPackage.BookingFlight.class, multiple = true)
	public restClientPackage.BookingFlight BookingFlightAbs2Conc(Integer EntryNum, String Name, Integer NoOfSeats){
		
		restClientPackage.BookingFlight output = new restClientPackage.BookingFlight();
		output.setPassengerName(Name);
		output.setFlightNumber("Flight - 1");
		output.setNoOfSeatsBooked(NoOfSeats);
		if(CRUDEFSM.getActionOnRun()=="Update")
			output.setPNR((Long) storage.getStorage("PNR", EntryNum));
		return output;
		
	}
	
	@Converter(label = "EntryNum2PNR", type = Integer.class, targetType = Long.class)
	public Long EntryNum2PNRConv(Integer input){
		
		long output = (Long) storage.getStorage("PNR", input);			
		return output;
		
	}
	
	@Converter(label = "EntryNum2Name", type = Integer.class, targetType = String.class)
	public String EntryNum2NameConv(Integer _null_){
		
		String output = (String) storage.getStorage("Name", (Integer)storage.topStorage("RandModelEntry"));
		return output;
		
	}
	
	@Converter(type = restClientPackage.BookingFlight.class, targetType = Object[].class)
	public Object[] BookingFlightConc2Abs(restClientPackage.BookingFlight input){
		if(input!=null){
			Object output[] = new Object[2];
			output[0] = input.getPassengerName();
			output[1] = input.getNoOfSeatsBooked();		
			return output;
		}
		return null;
		
	}
}

