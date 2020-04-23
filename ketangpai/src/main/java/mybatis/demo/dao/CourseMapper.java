package mybatis.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mybatis.demo.entity.Course;
import mybatis.demo.entity.Homework;
import mybatis.demo.security.model.User;

@Mapper
public interface CourseMapper {
	List<Course> findAllCourseByUser(int id);
	List<Course> teachingCourse(int id);
	List<Course> findAllFileCourse(int id);
	List<Homework> findHomeworkTop(String id);
	List<Homework> findHomeworkByCourseId(String id);
	
	User findTeacherById(int id); 
	Course showCourseDetail(String id);
	void addCourse(Course course);
	void addCourseTeacher(String courseId,int teacherId);
	void joinCourse(String courseId,int studentId);
	void updateCourse(Course course);
	void quitCourse(String courseId,int studentId);
	void deleteCourse(String courseId);
	void deleteCourseTeacher(String courseId,int teacherId);
	String getStuNum(String courseId);
	void fileAll(String courseId,int teacherId);
	void fileTeacher(String courseId,int teacherId);
	void fileStudent(String courseId,int studentId);
	void unFileTeacher(String courseId,int teacherId);
	void unFileStudent(String courseId,int studentId);
}
