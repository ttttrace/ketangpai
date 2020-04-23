package mybatis.demo.entity;

import java.util.List;

public class Course {
	private String id;
	private String courseName;
	private String className;
	private String term;
	private int teacherId;
	private TS teacher;
	private List<Homework> homeworks;
	public Course() {}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public TS getTeacher() {
		return teacher;
	}
	public void setTeacher(TS teacher) {
		this.teacher = teacher;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public List<Homework> getHomeworks() {
		return homeworks;
	}
	public void setHomeworks(List<Homework> homeworks) {
		this.homeworks = homeworks;
	}


	

}
