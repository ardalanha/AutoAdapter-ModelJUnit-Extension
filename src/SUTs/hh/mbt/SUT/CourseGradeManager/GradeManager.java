package hh.mbt.SUT.CourseGradeManager;
import java.util.ArrayList;


public class GradeManager {

	
	private ArrayList<StudentScore> studentscores = new ArrayList<StudentScore>();
	private Course course;
	
	public void setCourse(Course course){
		this.course = course;
		studentscores.clear();
	}
	
	public void add(StudentScore s){
		if(s.getCourse().getCourseName()==course.getCourseName() && s.getScore()>=0)
			studentscores.add(s);
		else
			System.out.println("add error: problem in course name or score / StudentScore object");
	}
	
	public StudentScore get(int index){
		return studentscores.get(index);
		
	}
	
	public void remove(int index){
		studentscores.remove(index);
		
	}
	
	public void replace(StudentScore s, int index){
		if(s.getCourse().getCourseName()==course.getCourseName() && s.getScore()>=0)
			studentscores.set(index, s);
		else
			System.out.println("replace error: problem in course name or score / StudentScore object");
		
	}

}
