package hh.mbt.SUT.CourseGradeManager;

public class StudentScore {
	
	private Student student =  null;
	
	private int score = -1;
	
	private Course course = null;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}




}
