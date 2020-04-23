package mybatis.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mybatis.demo.dao.CourseMapper;
import mybatis.demo.entity.Course;
import mybatis.demo.entity.Homework;
import mybatis.demo.security.model.User;

@Service
@Transactional
public class CourseService {
	
	@Autowired
	private CourseMapper courseMapper;
	
	public List<Course> findAllCourseByUser(int id) {
		List<Course> courseList=new ArrayList<>();
		courseList=courseMapper.findAllCourseByUser(id);
		for(int i=0;i<courseList.size();++i) {
			List<Homework> homeworks=new ArrayList<>();
			homeworks =courseMapper.findHomeworkTop(courseList.get(i).getId());
			courseList.get(i).setHomeworks(homeworks);
		}
		return courseList;
	}
	
	public List<Course> teachingCourse(int id){
		List<Course> courseList=new ArrayList<>();
		courseList=courseMapper.teachingCourse(id);
		for(int i=0;i<courseList.size();++i) {
			List<Homework> homeworks=new ArrayList<>();
			homeworks =courseMapper.findHomeworkTop(courseList.get(i).getId());
			courseList.get(i).setHomeworks(homeworks);
		}
		return courseList;
	}
	
	public List<Course> findAllFileCourse(int id){
		return courseMapper.findAllFileCourse(id);
	}
	
	public User findTeacherById(int id) {
		return this.courseMapper.findTeacherById(id);
	} 
	
	public Course showCourseDetail(String id) {
		return this.courseMapper.showCourseDetail(id);
	}
	
	public void addCourse(Course course) {
		courseMapper.addCourse(course);
	}
	
	public void addCourseTeacher(String courseId,int teacherId) {
		courseMapper.addCourseTeacher(courseId, teacherId);
	}
	
	public void updateCourse(Course course) {
		courseMapper.updateCourse(course);
	}
	public void quitCourse(String courseId,int studentId) {
		courseMapper.quitCourse(courseId, studentId);
	}
	
	public void joinCourse(String courseId,int studentId) {
		courseMapper.joinCourse(courseId, studentId);
	}
	
	public void deleteCourse(String courseId) {
		courseMapper.deleteCourse(courseId);
	}
	public void deleteCourseTeacher(String courseId,int teacherId) {
		courseMapper.deleteCourseTeacher(courseId, teacherId);
	}
	public String getStuNum(String courseId) {
		return courseMapper.getStuNum(courseId);
	}
	public void fileAll(String courseId,int teacherId) {
		courseMapper.fileAll(courseId, teacherId);
	}
	public void fileTeacher(String courseId,int teacherId) {
		courseMapper.fileTeacher(courseId, teacherId);
	}
	public void fileStudent(String courseId,int studentId) {
		courseMapper.fileStudent(courseId, studentId);
	}
	public void unFileTeacher(String courseId,int teacherId) {
		courseMapper.unFileTeacher(courseId, teacherId);
	}
	public void unFileStudent(String courseId,int studentId) {
		courseMapper.unFileStudent(courseId, studentId);
	}
}
