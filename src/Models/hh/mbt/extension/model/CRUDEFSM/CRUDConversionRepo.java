package hh.mbt.extension.model.CRUDEFSM;

import hh.mbt.extension.Converter;
import hh.mbt.extension.DataStorage;

public class CRUDConversionRepo {
	
	private DataStorage storage = new DataStorage();

	
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
	
	@Converter(type = Integer.class, targetType = Long.class)
	public Long EntryNum2PNRConv(Integer input){
		
		long output = (Long) storage.getStorage("PNR", input);			
		return output;
		
	}
	
	@Converter(type = Integer.class, targetType = String.class)
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