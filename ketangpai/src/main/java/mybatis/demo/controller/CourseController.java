package mybatis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mybatis.demo.entity.Course;
import mybatis.demo.entity.Homework;
import mybatis.demo.entity.TS;
import mybatis.demo.security.model.User;
import mybatis.demo.security.service.UserService;
import mybatis.demo.service.CourseService;
import mybatis.demo.service.HomeworkService;

@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private HomeworkService homeworkService;
	
	@Transactional
	@PostMapping("addCourse")
	public void addCourse(@RequestBody(required = false) Course course) {
		courseService.addCourse(course);
		courseService.addCourseTeacher(course.getId(), course.getTeacherId());
	}
	
	@Transactional
	@PostMapping("deleteCourse")
	public void deleteCourse(String courseId,int teacherId) {
		courseService.deleteCourse(courseId);
		courseService.deleteCourseTeacher(courseId, teacherId);
	}
	
	@Transactional
	@PostMapping("joinCourse")
	public void joinCourse(String courseId,int studentId) {
		courseService.joinCourse(courseId, studentId);
		TS student=userService.getUser(studentId);
		List<Homework> works= homeworkService.getHomeworks(courseId);
		for(int i=0;i<works.size();++i) {
			homeworkService.addnewStu(works.get(i).getId(), student.getId(), student.getTsid(), student.getName());
		} 
	}
	
	@Transactional
	@PostMapping("updateCourse")
	public void updateCpurse(@RequestBody(required = false) Course course) {
		courseService.updateCourse(course);	
	}
	
	@Transactional
	@PostMapping("quitCourse")
	public void quitCourse(String courseId,int studentId) {
		courseService.quitCourse(courseId, studentId);
	}
	
	@GetMapping("showCourseDetail")
	public Course showCourseDetail(String id) {
		return courseService.showCourseDetail(id);
	}
	
	@GetMapping("findTeacherByCourse")
	public User findTeacherByCourse(int id) {
		return courseService.findTeacherById(id);
	}
	@GetMapping("findAllTeacherCourse")
	public List<Course> teachingCourse(int id){
		return this.courseService.teachingCourse(id);
	}
	@GetMapping("findAllCourseByUser")
	public List<Course> findAllCourseByUser(int id) {
		return courseService.findAllCourseByUser(id);
	}
	@GetMapping("findAllFileCourse")
	List<Course> findAllFileCourse(int id){
		return courseService.findAllFileCourse(id);
	}
	@GetMapping("getStuNum")
	public String getStuNum(String courseId) {
		return courseService.getStuNum(courseId);
	}
	@PostMapping("fileAll")
	public void fileAll(String courseId,int teacherId) {
		courseService.fileAll(courseId, teacherId);
	}
	@PostMapping("fileTeacher")
	public void fileTeacher(String courseId,int teacherId) {
		courseService.fileTeacher(courseId, teacherId);
	}
	@PostMapping("fileStudent")
	public void fileStudent(String courseId,int studentId) {
		courseService.fileStudent(courseId, studentId);
	}
	@PostMapping("unFileTeacher")
	public void unFileTeacher(String courseId,int teacherId) {
		courseService.unFileTeacher(courseId, teacherId);
	}
	@PostMapping("unFileStudent")
	public void unFileStudent(String courseId,int studentId) {
		courseService.unFileStudent(courseId, studentId);
	}
}
