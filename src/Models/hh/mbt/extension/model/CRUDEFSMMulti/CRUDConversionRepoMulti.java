package hh.mbt.extension.model.CRUDEFSMMulti;

import hh.mbt.SUT.CourseGradeManager.Course;
import hh.mbt.SUT.CourseGradeManager.Student;
import hh.mbt.SUT.CourseGradeManager.StudentScore;
import hh.mbt.extension.Converter;
import hh.mbt.extension.DataStorage;

public class CRUDConversionRepoMulti {
	
	private DataStorage storage = new DataStorage();

	
	//CRUD - FlightBooking Converters
	@Converter(targetType = restClientPackage.BookingFlight.class, multiple = true)
	public restClientPackage.BookingFlight BookingFlightAbs2Conc(Integer EntryNum, String Name, Integer NoOfSeats){
		
		restClientPackage.BookingFlight output = new restClientPackage.BookingFlight();
		output.setPassengerName(Name);
		output.setFlightNumber("Flight - 1");
		output.setNoOfSeatsBooked(NoOfSeats);
		if(CRUDEFSMMulti.getActionOnRun()=="Update")
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
	
	//CRUD - Grade Manager Converters
	@Converter(type = String.class, targetType = Course.class)
	public Course Str2CourseConv(String input){
		
		Course output = new Course();
		output.setCourseName(input);
		return output;
		
	}
	
	@Converter(targetType = StudentScore.class, multiple = true)
	public StudentScore StudentScoreAbs2Conc(Integer notUsed, String Name, Integer Score){
		
		StudentScore output = new StudentScore();
		Course course = new Course();
		course.setCourseName((String) storage.topStorage("CourseName"));
		output.setCourse(course);
		Student student = new Student();
		student.setFirstName(Name);
		output.setStudent(student);
		output.setScore(Score);
		
		return output;
		
	}
	
	@Converter(type = StudentScore.class, targetType = Object[].class)
	public Object[] StudentScoreConc2Abs(StudentScore input){
		Object output[] = new Object[2];
		Student student;
		student = input.getStudent();
		output[0] = student.getFirstName();
		output[1] = input.getScore();		
		return output;
		
	}
	
	

}
